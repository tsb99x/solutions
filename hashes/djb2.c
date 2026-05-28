#include <stdio.h>

int main(void) {
  unsigned long hash = 5381;
  int c;

  while ((c = getchar()) != EOF)
    if (c == '\n') {
      printf("%lu\n", hash);
      hash = 0;
    } else
      hash = ((hash << 5) + hash) + c;

  if (hash != 0)
    printf("%lu\n", hash);

  return 0;
}

/*

$ gcc hashes_djb2.c
$ cat /usr/share/dict/words | wc -l
104334
$ cat /usr/share/dict/words | ./a.out | wc -l
104334
$ cat /usr/share/dict/words | ./a.out | sort | uniq | wc -l
104269

*/
