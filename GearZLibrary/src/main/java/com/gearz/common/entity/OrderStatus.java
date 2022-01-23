package com.gearz.common.entity;

public enum OrderStatus {
    NEW {
        @Override
        public String defaultDescription() {
            return "Order accepted";
        }
    },
    CANCELLED {
        @Override
        public String defaultDescription() {
            return "Order dropped";
        }
    },
    PROCESSING {
        @Override
        public String defaultDescription() {
            return "In progress";
        }
    },
    PACKAGED {
        @Override
        public String defaultDescription() {
            return "Parcel ready for dispatch";
        }
    },
    PICKED {
        @Override
        public String defaultDescription() {
            return "Parcel dispatched";
        }
    },
    SHIPPING {
        @Override
        public String defaultDescription() {
            return "Out for delivery";
        }
    },
    DELIVERED {
        @Override
        public String defaultDescription() {
            return "Order arrived";
        }
    },
    RETURN_REQUESTED {
        @Override
        public String defaultDescription() {
            return "Customer requested order return";
        }
    },
    RETURNED {
        @Override
        public String defaultDescription() {
            return "Order returned";
        }
    },
    PAID {
        @Override
        public String defaultDescription() {
            return "Order was paid";
        }
    },
    REFUNDED {
        @Override
        public String defaultDescription() {
            return "Order refunded";
        }
    };

    public abstract String defaultDescription();
}
