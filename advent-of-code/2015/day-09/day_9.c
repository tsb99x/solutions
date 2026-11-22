/*
        https://adventofcode.com/2015/day/9
        --- Day 9: All in a Single Night ---

        $ gcc -O2 -ansi \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_9{,.c} \
                && ./advent_of_code_2015_9
*/

#include <assert.h>
#include <limits.h>
#include <stdio.h>
#include <string.h>

int add_town_to_set(char towns[64][255], int *towns_size, const char *town)
{
        int i;

        for (i = 0; i < *towns_size; i++)
                if (!strcmp(towns[i], town))
                        return i;
        strcpy(towns[i], town);
        (*towns_size)++;
        return i;
}

int prepare_costs_matrix(int costs_matrix[64][64], char towns[64][255],
                         const char *distances)
{
        int d, chs, i1, i2;
        char t1[255], t2[255];
        int towns_size = 0;

        while (sscanf(distances, "%s to %s = %d %n", t1, t2, &d, &chs) == 3) {
                i1 = add_town_to_set(towns, &towns_size, t1);
                i2 = add_town_to_set(towns, &towns_size, t2);
                costs_matrix[i1][i2] = d;
                costs_matrix[i2][i1] = d;
                distances += chs;
        }
        return towns_size;
}

int min(int a, int b) { return a < b ? a : b; }

int index_of(int *arr, int arr_len, int elem)
{
        int i;
        for (i = 0; i < arr_len; i++)
                if (arr[i] == elem)
                        return i;
        return -1;
}

int find_path(int costs_matrix[64][64], int towns_size, int path[64],
              int path_size, int new_town, int current_path_cost)
{
        int i, cost_to_next;
        int r = INT_MAX;

        path[path_size++] = new_town;

        if (path_size == towns_size)
                return current_path_cost;

        for (i = 0; i < towns_size; i++) {
                cost_to_next = costs_matrix[new_town][i];
                if (cost_to_next == 0)
                        continue;
                if (index_of(path, path_size, i) >= 0)
                        continue;
                r = min(r, find_path(costs_matrix, towns_size, path, path_size,
                                     i, current_path_cost + cost_to_next));
        }
        return r;
}

int shortest_distance(const char *distances)
{
        int i, towns_size;
        char towns[64][255] = {{0}};
        int costs_matrix[64][64] = {{0}};
        int path[64] = {0};
        int r = INT_MAX;

        towns_size = prepare_costs_matrix(costs_matrix, towns, distances);
        for (i = 0; i < towns_size; i++)
                r = min(r, find_path(costs_matrix, towns_size, path, 0, i, 0));
        return r;
}

int main(void)
{
        const char *distances = "London to Dublin = 464\n"
                                "London to Belfast = 518\n"
                                "Dublin to Belfast = 141";
        assert(shortest_distance(distances) == 605);
        return 0;
}
