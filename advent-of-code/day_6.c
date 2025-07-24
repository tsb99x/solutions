/*
        https://adventofcode.com/2015/day/6
        --- Day 6: Probably a Fire Hazard ---

        $ gcc -ansi -o advent_of_code_2015_6{,.c} \
                && ./advent_of_code_2015_6
*/

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

enum operation {
        turn_on,
        turn_off,
        toggle,
};

int how_many_lights_are_lit(const char *cmd)
{
        int grid[1000][1000] = {{0}};
        /* memset((void *)grid, 0, sizeof(grid)); */

        int x, y, start_x, start_y, end_x, end_y;
        enum operation op;

        if (sscanf(cmd, "turn on %d,%d through %d,%d", &start_x, &start_y,
                   &end_x, &end_y)) {
                op = turn_on;
        } else if (sscanf(cmd, "turn off %d,%d through %d,%d", &start_x,
                          &start_y, &end_x, &end_y)) {
                op = turn_off;
        } else if (sscanf(cmd, "toggle %d,%d through %d,%d", &start_x, &start_y,
                          &end_x, &end_y)) {
                op = toggle;
        } else
                abort();

        for (y = start_y; y <= end_y; y++)
                for (x = start_x; x <= end_x; x++)
                        switch (op) {
                        case turn_on:
                                grid[y][x] = 1;
                                break;
                        case turn_off:
                                grid[y][x] = 0;
                                break;
                        case toggle:
                                grid[y][x] = !grid[y][x];
                                break;
                        }

        int count = 0;
        for (y = 0; y < 1000; y++)
                for (x = 0; x < 1000; x++)
                        if (grid[y][x])
                                count++;
        return count;
}

int main(void)
{
        assert(how_many_lights_are_lit("turn on 0,0 through 999,999") ==
               1000000);
        assert(how_many_lights_are_lit("toggle 0,0 through 999,0") == 1000);
        assert(how_many_lights_are_lit("turn off 499,499 through 500,500") ==
               0);
        return 0;
}
