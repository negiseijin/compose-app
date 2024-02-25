package org.company.app.ui.screens.routine

import io.ktor.util.date.WeekDay

internal object RoutineUtils {
    fun dummyData(): Payload {
        return Payload(
            timeState =
                listOf(
                    TimeState(
                        day = WeekDay.SUNDAY,
                    ),
                    TimeState(
                        day = WeekDay.MONDAY,
                        hour = (0..11).random(),
                        minute = (0..59).random(),
                        isEnabled = true,
                    ),
                    TimeState(
                        day = WeekDay.TUESDAY,
                        hour = (0..11).random(),
                        minute = (0..59).random(),
                    ),
                    TimeState(
                        day = WeekDay.WEDNESDAY,
                        hour = (0..11).random(),
                        minute = (0..59).random(),
                        isEnabled = true,
                    ),
                    TimeState(
                        day = WeekDay.THURSDAY,
                    ),
                    TimeState(
                        day = WeekDay.FRIDAY,
                    ),
                    TimeState(
                        day = WeekDay.SATURDAY,
                    ),
                ),
        )
    }

    private val DAY_MAP: Map<WeekDay, String> =
        mapOf(
            WeekDay.SUNDAY to "日曜日",
            WeekDay.MONDAY to "月曜日",
            WeekDay.TUESDAY to "火曜日",
            WeekDay.WEDNESDAY to "水曜日",
            WeekDay.THURSDAY to "木曜日",
            WeekDay.FRIDAY to "金曜日",
            WeekDay.SATURDAY to "土曜日",
        )

    fun getDayName(day: WeekDay): String {
        return DAY_MAP[day].toString()
    }
}