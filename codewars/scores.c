int score(const int dice[5]) {
  int i, total = 0, counts[6] = { 0, 0, 0, 0, 0, 0 };

  for (i = 0; i < 5; i++)
    counts[dice[i] - 1]++;

  if (counts[0] >= 3) {
    total += 1000;
    counts[0] -= 3;
  }

  if (counts[5] >= 3) {
    total += 600;
    counts[5] -= 3;
  }

  if (counts[4] >= 3) {
    total += 500;
    counts[4] -= 3;
  }

  if (counts[3] >= 3) {
    total += 400;
    counts[3] -= 3;
  }

  if (counts[2] >= 3) {
    total += 300;
    counts[2] -= 3;
  }

  if (counts[1] >= 3) {
    total += 200;
    counts[1] -= 3;
  }

  while (counts[0] > 0) {
    total += 100;
    counts[0] -= 1;
  }

  while (counts[4] > 0) {
    total += 50;
    counts[4] -= 1;
  }

  return total;
}
