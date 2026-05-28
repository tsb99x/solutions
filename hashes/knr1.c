#include <stdio.h>

int main(void) {
  unsigned int hash = 0;
  int c;

  while ((c = getchar()) != EOF)
    if (c == '\n') {
      printf("%u\n", hash);
      hash = 0;
    } else
      hash += c;

  if (hash != 0)
    printf("%u\n", hash);

  return 0;
}

/*

$ gcc hashes_knr1.c
$ cat /usr/share/dict/words | ./a.out | sort | uniq | wc -l
1843
$ cat /usr/share/dict/words | ./a.out | wc -l
104334
$ cat /usr/share/dict/words | wc -l
104334

*/
