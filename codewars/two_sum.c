void twoSum(unsigned count, const int numbers[count], int target,
			      unsigned *index1, unsigned *index2)
{
  int i, j;

	*index1 = *index2 = 0;
  for (i = 0; i < count; i++)
    for (j = i + 1; j < count; j++)
      if (numbers[i] + numbers[j] == target) {
        *index1 = i;
        *index2 = j;
      }
}
