package com.gearz.admin.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gearz.admin.repository.CategoryRepository;
import com.gearz.common.entity.Category;
import com.gearz.common.exception.CategoryNotFoundException;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	public List<Category> listAllCategories(String categoriesSortDir) {
		Sort sort = Sort.by("name");

		if (categoriesSortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (categoriesSortDir.equals("desc")) {
			sort = sort.descending();
		}

		List<Category> rootCategories = categoryRepo.getRootCategories(sort);
		return listHierarchicalCategories(rootCategories, categoriesSortDir);
	}

	public List<Category> listAllCategoriesWithoutHierarchicalStructure() {
		return (List<Category>) categoryRepo.findAll();
	}

	public Category saveCategory(Category category) {
		Category parentCategory = category.getParent();
		if (parentCategory != null) {
			String all_parent_ids = parentCategory.getAllParentIDs() == null ? "-" : parentCategory.getAllParentIDs();
			all_parent_ids += String.valueOf(parentCategory.getId()) + "-";
			category.setAllParentIDs(all_parent_ids);
		}
		return categoryRepo.save(category);
	}

	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		categoryRepo.updateCategoryStatus(id, enabled);
	}

	public void deleteCategory(Integer id) throws CategoryNotFoundException {
		Long countById = categoryRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}
		categoryRepo.deleteById(id);
	}

	private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
		List<Category> hierarchicalCategories = new ArrayList<>();

		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.getCategory(rootCategory, rootCategory.getName()));
			Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);
			for (Category subCategory : children) {
				String name = ">>" + subCategory.getName();
				hierarchicalCategories.add(Category.getCategory(subCategory, name));
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
			}
		}

		return hierarchicalCategories;
	}

	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel,
			String sortDir) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);
		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += ">>";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.getCategory(subCategory, name));
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
		}
	}

	public List<Category> categoryDropDownList() {
		List<Category> categoriesInForm = new ArrayList<>();
		Iterable<Category> savedCategories = categoryRepo.getRootCategories(Sort.by("name").ascending());

		for (Category category : savedCategories) {
			if (category.getParent() == null) {
				categoriesInForm.add(Category.getIdAndName(category));
				Set<Category> children = sortSubCategories(category.getChildren());
				for (Category subCategory : children) {
					String name = ">>" + subCategory.getName();
					categoriesInForm.add(Category.getIdAndName(subCategory.getId(), name));
					listChildren(categoriesInForm, subCategory, 1);
				}
			}
		}

		return categoriesInForm;
	}

	private void listChildren(List<Category> categoriesInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += ">>";
			}
			name += subCategory.getName();
			categoriesInForm.add(Category.getIdAndName(subCategory.getId(), name));
			listChildren(categoriesInForm, subCategory, newSubLevel);
		}
	}

	public Category getCategoryById(Integer id) throws CategoryNotFoundException {
		try {
			return categoryRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("No category ID " + id + " was found");
		}
	}

	public String isCategoryExisted(Integer id, String name, String tag) {
		boolean isNewCategory = (id == null || id == 0);
		Category categoryName = categoryRepo.findByName(name);

		if (isNewCategory) {
			if (categoryName != null) {
				return "Duplicate Name";
			} else {
				Category categoryTag = categoryRepo.findByTag(tag);
				if (categoryTag != null) {
					return "Duplicate Tag";
				}
			}
		} else {
			if (categoryName != null && categoryName.getId() != id) {
				return "Duplicate Name";
			}
			Category categoryTag = categoryRepo.findByTag(tag);
			if (categoryTag != null && categoryTag.getId() != id) {
				return "Duplicate Tag";
			}
		}

		return "OK";
	}

	private SortedSet<Category> sortSubCategories(Set<Category> children) {
		return sortSubCategories(children, "asc");
	}

	private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
		SortedSet<Category> sortedChildrenSet = new TreeSet<>(new Comparator<Category>() {
			@Override
			public int compare(Category c1, Category c2) {
				if (sortDir.equals("asc")) {
					return c1.getName().compareTo(c2.getName());
				} else {
					return c2.getName().compareTo(c1.getName());
				}
			}
		});
		sortedChildrenSet.addAll(children);
		return sortedChildrenSet;
	}
}