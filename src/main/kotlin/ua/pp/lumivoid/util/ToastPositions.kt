package ua.pp.lumivoid.util

@Suppress("unused")
enum class ToastPositions {
    TOP_LEFT {
        override fun xPos(): Int = 0
        override fun hidedXPos(): Int = -100
        override fun yPos(): Int = 0
    },
    TOP_MIDDLE_LEFT {
        override fun xPos(): Int = 0
        override fun hidedXPos(): Int = -100
        override fun yPos(): Int = 35
    },
    MIDDLE_LEFT {
        override fun xPos(): Int = 0
        override fun hidedXPos(): Int = - 100
        override fun yPos(): Int = 50
    },
    BOTTOM_MIDDLE_LEFT {
        override fun xPos(): Int = 0
        override fun hidedXPos(): Int = -100
        override fun yPos(): Int = 65
    },
    BOTTOM_LEFT {
        override fun xPos(): Int = 0
        override fun hidedXPos(): Int = -100
        override fun yPos(): Int = 100
    },
    TOP_RIGHT {
        override fun xPos(): Int = 100
        override fun hidedXPos(): Int = 200
        override fun yPos(): Int = 0
    },
    TOP_MIDDLE_RIGHT {
        override fun xPos(): Int = 100
        override fun hidedXPos(): Int = 200
        override fun yPos(): Int = 35
    },
    MIDDLE_RIGHT {
        override fun xPos(): Int = 100
        override fun hidedXPos(): Int = 200
        override fun yPos(): Int = 50
    },
    BOTTOM_MIDDLE_RIGHT {
        override fun xPos(): Int = 100
        override fun hidedXPos(): Int = 200
        override fun yPos(): Int = 65
    },
    BOTTOM_RIGHT {
        override fun xPos(): Int = 100
        override fun hidedXPos(): Int = 200
        override fun yPos(): Int = 100
    };

    abstract fun xPos(): Int
    abstract fun hidedXPos(): Int
    abstract fun yPos(): Int
}