.DELETE_ON_ERROR:

SRCS = $(wildcard *.c)
BINS = $(SRCS:%.c=%)

all: $(BINS)
	$(foreach BIN, $(BINS), $(call test))

clean:
	rm -f $(BINS)

.PHONY: all clean

define test
	$(eval UNIQS := $(shell cat /usr/share/dict/words | ./$(BIN) | sort | uniq | wc -l))
	$(eval TOTAL := $(shell cat /usr/share/dict/words | wc -l))
	@echo "$(BIN) distinct hashes: $(UNIQS) of $(TOTAL) words"
endef
