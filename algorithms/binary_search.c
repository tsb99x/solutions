#include <stddef.h>
#include <stdio.h>

const int *find_index(
    int val,
    const int *arr,
    size_t arr_size
) {
    int lo = 0;
    int hi = arr_size - 1;

    while (lo <= hi) {
        int mi = (lo + hi) / 2;

        if (arr[mi] == val)
            return arr + mi;
        if (arr[mi] > val)
            hi = mi - 1;
        else
            lo = mi + 1;
    }
    return NULL;
}

int main(
    void
) {
    const int arr[] = { 1, 3, 5, 8, 12 };
    size_t arr_size = sizeof(arr) / sizeof(*arr);
    int needle = 8;
    const int *res = find_index(needle, arr, arr_size);

    printf("%d is %sfound in array\n", needle, !res ? "not " : "");
    return 0;
}
