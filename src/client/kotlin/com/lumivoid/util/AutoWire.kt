package com.lumivoid.util

enum class AutoWire {
    AUTO_REDSTONE {
        override fun place(): String {
            return "AUTO_REDSTONE"
        }
    },
    AUTO_LINE {
        override fun place(): String {
            return "AUTO_LINE"
        }
    },
    AUTO_REPEATER {
        override fun place(): String {
            return "AUTO_REPEATER"
        }
    },
    AUTO_COMPARATOR {
        override fun place(): String {
            return "AUTO_COMPARATOR"
        }
    },
    CHEAP_AUTO_COMPARATOR {
        override fun place(): String {
            return "COMPACT_AUTO_COMPARATOR"
        }
    };

    abstract fun place(): String
}