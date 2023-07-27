package me.odinclient.utils.skyblock

import net.minecraft.util.Vec3i

object IceFillFloors {
    // Array of Pairs with one block that should be air and one that shouldn't, this is to speed up scanning
    val representativeFloors = arrayOf(
        arrayOf(
            Pair(Vec3i(0, 0, -1), Vec3i(2, 0, -1)),
            Pair(Vec3i(0, 0, 1), Vec3i(2, 0, 1)),
            Pair(Vec3i(2, 0, 1), Vec3i(0, 0, 1)),
            Pair(Vec3i(2, 0, -1), Vec3i(0, 0, -1))
        ),

        arrayOf(
            Pair(Vec3i(1, 0, 0), Vec3i(1, 0, -1)),
            Pair(Vec3i(2, 0, 1), Vec3i(2, 0, 0)),
            Pair(Vec3i(2, 0, 1), Vec3i(1, 0, -1)),
            Pair(Vec3i(0, 0, -2), Vec3i(1, 0, 0)),
            Pair(Vec3i(3, 0, 0), Vec3i(0, 0, -2)),
            Pair(Vec3i(2, 0, -1), Vec3i(2, 0, 0)),
        ),

        arrayOf(
            Pair(Vec3i(4, 0, 2), Vec3i(4, 0, 1)),

            Pair(Vec3i(3, 0, -1), Vec3i(2, 0, -1)),

            Pair(Vec3i(2, 0, 3), Vec3i(2, 0, 2)),

            Pair(Vec3i(3, 0, 0), Vec3i(3, 0, -1)),

            Pair(Vec3i(3, 0, 2), Vec3i(3, 0, 1)),
        ),


    )


    val floors = arrayOf(
        arrayOf(
            arrayOf(
                Vec3i(0, 0, -1),
                Vec3i(1, 0, -1),
                Vec3i(1, 0, 0),
                Vec3i(1, 0, 1),
                Vec3i(2, 0, 1),
                Vec3i(2, 0, 0),
                Vec3i(3, 0, 0)
            ),
            arrayOf(
                Vec3i(0, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(1, 0, 0),
                Vec3i(1, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(2, 0, 0),
                Vec3i(3, 0, 0)
            ),
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(1, 0, 1),
                Vec3i(2, 0, 1),
                Vec3i(2, 0, 0),
                Vec3i(3, 0, 0)
            ),
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(1, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(2, 0, 0),
                Vec3i(3, 0, 0)
            )
        ),
        arrayOf(
            arrayOf(
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(3, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(2, 0, 0),
                Vec3i(1, 0, 0),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(4, 0, 1),
                Vec3i(3, 0, 1),
                Vec3i(3, 0, 0),
                Vec3i(4, 0, 0),
                Vec3i(5, 0, 0)
            ),
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(1, 0, -1),
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(3, 0, -1),
                Vec3i(3, 0, 0),
                Vec3i(3, 0, 1),
                Vec3i(2, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 0),
                Vec3i(5, 0, 0),
            ),
            arrayOf(
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(3, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(2, 0, 0),
                Vec3i(2, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 0),
                Vec3i(5, 0, 0),
            ),
            arrayOf(
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(1, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(2, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(3, 0, -1),
                Vec3i(3, 0, 0),
                Vec3i(2, 0, 0),
                Vec3i(2, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 0),
                Vec3i(5, 0, 0),
            ),
            arrayOf(
                Vec3i(0, 0, -1),
                Vec3i(1, 0, -1),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(2, 0, -1),
                Vec3i(3, 0, -1),
                Vec3i(3, 0, 0),
                Vec3i(2, 0, 0),
                Vec3i(2, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 0),
                Vec3i(5, 0, 0),
            ),
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(4, 0, 1),
                Vec3i(3, 0, 1),
                Vec3i(3, 0, 0),
                Vec3i(3, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(1, 0, -1),
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(4, 0, 0),
                Vec3i(5, 0, 0),
            )
        ),
        arrayOf(
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(2, 0, 0),
                Vec3i(3, 0, 0),
                Vec3i(3, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(1, 0, -1),
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(1, 0, -3),
                Vec3i(2, 0, -3),
                Vec3i(2, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(3, 0, -3),
                Vec3i(4, 0, -3),
                Vec3i(5, 0, -3),
                Vec3i(6, 0, -3),
                Vec3i(6, 0, -2),
                Vec3i(5, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(5, 0, -1),
                Vec3i(5, 0, 0),
                Vec3i(5, 0, 1),
                Vec3i(5, 0, 2),
                Vec3i(4, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(3, 0, 1),
                Vec3i(2, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(0, 0, 3),
                Vec3i(1, 0, 3),
                Vec3i(1, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(2, 0, 3),
                Vec3i(3, 0, 3),
                Vec3i(4, 0, 3),
                Vec3i(5, 0, 3),
                Vec3i(6, 0, 3),
                Vec3i(6, 0, 2),
                Vec3i(6, 0, 1),
                Vec3i(6, 0, 0),
                Vec3i(7, 0, 0),
            ),
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(1, 0, -1),
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(0, 0, -3),
                Vec3i(1, 0, -3),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(2, 0, -3),
                Vec3i(3, 0, -3),
                Vec3i(4, 0, -3),
                Vec3i(5, 0, -3),
                Vec3i(6, 0, -3),
                Vec3i(6, 0, -2),
                Vec3i(5, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(3, 0, -1),
                Vec3i(4, 0, -1),
                Vec3i(4, 0, 0),
                Vec3i(3, 0, 0),
                Vec3i(3, 0, 1),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(0, 0, 3),
                Vec3i(1, 0, 3),
                Vec3i(2, 0, 3),
                Vec3i(3, 0, 3),
                Vec3i(4, 0, 3),
                Vec3i(5, 0, 3),
                Vec3i(6, 0, 3),
                Vec3i(6, 0, 2),
                Vec3i(5, 0, 2),
                Vec3i(5, 0, 1),
                Vec3i(5, 0, 0),
                Vec3i(6, 0, 0),
                Vec3i(7, 0, 0),
            ),
            arrayOf(
                Vec3i(0, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(1, 0, 2),
                Vec3i(0, 0, 2),
                Vec3i(0, 0, 3),
                Vec3i(1, 0, 3),
                Vec3i(2, 0, 3),
                Vec3i(3, 0, 3),
                Vec3i(4, 0, 3),
                Vec3i(5, 0, 3),
                Vec3i(5, 0, 2),
                Vec3i(6, 0, 2),
                Vec3i(6, 0, 1),
                Vec3i(5, 0, 1),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(3, 0, 1),
                Vec3i(3, 0, 0),
                Vec3i(2, 0, 0),
                Vec3i(2, 0, -1),
                Vec3i(1, 0, -1),
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(0, 0, -3),
                Vec3i(1, 0, -3),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(2, 0, -3),
                Vec3i(3, 0, -3),
                Vec3i(4, 0, -3),
                Vec3i(5, 0, -3),
                Vec3i(6, 0, -3),
                Vec3i(6, 0, -2),
                Vec3i(5, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(3, 0, -2),
                Vec3i(3, 0, -1),
                Vec3i(4, 0, -1),
                Vec3i(5, 0, -1),
                Vec3i(5, 0, 0),
                Vec3i(6, 0, 0),
                Vec3i(7, 0, 0),
            ),
            arrayOf(
                Vec3i(1, 0, 0),
                Vec3i(1, 0, -1),
                Vec3i(0, 0, -1),
                Vec3i(0, 0, -2),
                Vec3i(0, 0, -3),
                Vec3i(1, 0, -3),
                Vec3i(1, 0, -2),
                Vec3i(2, 0, -2),
                Vec3i(2, 0, -3),
                Vec3i(3, 0, -3),
                Vec3i(4, 0, -3),
                Vec3i(5, 0, -3),
                Vec3i(6, 0, -3),
                Vec3i(6, 0, -2),
                Vec3i(5, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(5, 0, -1),
                Vec3i(5, 0, 0),
                Vec3i(4, 0, 0),
                Vec3i(3, 0, 0),
                Vec3i(2, 0, 0),
                Vec3i(2, 0, 1),
                Vec3i(3, 0, 1),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(1, 0, 2),
                Vec3i(0, 0, 2),
                Vec3i(0, 0, 3),
                Vec3i(1, 0, 3),
                Vec3i(2, 0, 3),
                Vec3i(3, 0, 3),
                Vec3i(4, 0, 3),
                Vec3i(5, 0, 3),
                Vec3i(6, 0, 3),
                Vec3i(6, 0, 2),
                Vec3i(6, 0, 1),
                Vec3i(6, 0, 0),
                Vec3i(7, 0, 0),
            ),
            arrayOf(
                Vec3i(0, 0, -1),
                Vec3i(1, 0, -1),
                Vec3i(2, 0, -1),
                Vec3i(2, 0, -2),
                Vec3i(1, 0, -2),
                Vec3i(0, 0, -2),
                Vec3i(0, 0, -3),
                Vec3i(1, 0, -3),
                Vec3i(2, 0, -3),
                Vec3i(3, 0, -3),
                Vec3i(4, 0, -3),
                Vec3i(5, 0, -3),
                Vec3i(6, 0, -3),
                Vec3i(6, 0, -2),
                Vec3i(5, 0, -2),
                Vec3i(4, 0, -2),
                Vec3i(4, 0, -1),
                Vec3i(5, 0, -1),
                Vec3i(5, 0, 0),
                Vec3i(5, 0, 1),
                Vec3i(4, 0, 1),
                Vec3i(4, 0, 2),
                Vec3i(3, 0, 2),
                Vec3i(2, 0, 2),
                Vec3i(2, 0, 1),
                Vec3i(1, 0, 1),
                Vec3i(0, 0, 1),
                Vec3i(0, 0, 2),
                Vec3i(0, 0, 3),
                Vec3i(1, 0, 3),
                Vec3i(2, 0, 3),
                Vec3i(3, 0, 3),
                Vec3i(4, 0, 3),
                Vec3i(5, 0, 3),
                Vec3i(5, 0, 2),
                Vec3i(6, 0, 2),
                Vec3i(6, 0, 1),
                Vec3i(6, 0, 0),
                Vec3i(7, 0, 0),
            )
        )
    )
}