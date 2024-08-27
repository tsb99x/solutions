#include <stddef.h>
#include <math.h>

double mean_square_error(size_t n, const int a[n], const int b[n]) {
  int i, diff, sq_sum;

  sq_sum = 0;
  for (i = 0; i < n; i++) {
    diff = abs(a[i] - b[i]);
    sq_sum += diff * diff;
  }

  return (double) sq_sum / n;
}
