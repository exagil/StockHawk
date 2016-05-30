package com.sam_chordas.android.stockhawk.data.models;

public enum PersistableBoolean {
    TRUE {
        public int value() {
            return 1;
        }
    },
    FALSE {
        public int value() {
            return 0;
        }
    };

    public int value() {
        return this.value();
    }
}
