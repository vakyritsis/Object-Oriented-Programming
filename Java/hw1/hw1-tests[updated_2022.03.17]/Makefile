
tiny_out:
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-i.in    > tests/tiny-i.out
	diff -urN tests/tiny-i.out tests/tiny-i.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-id.in   > tests/tiny-id.out
	diff -urN tests/tiny-id.out tests/tiny-id.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-idi.in  > tests/tiny-idi.out
	diff -urN tests/tiny-idi.out tests/tiny-idi.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-iw-1.in > tests/tiny-iw-1.out
	diff -urN tests/tiny-iw-1.out tests/tiny-iw-1.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-iw-2.in > tests/tiny-iw-2.out
	diff -urN tests/tiny-iw-2.out tests/tiny-iw-2.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-is-1.in > tests/tiny-is-1.out
	diff -urN tests/tiny-is-1.out tests/tiny-is-1.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-is-2.in > tests/tiny-is-2.out
	diff -urN tests/tiny-is-2.out tests/tiny-is-2.std

tiny_std:
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-i.in    > tests/tiny-i.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-id.in   > tests/tiny-id.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-idi.in  > tests/tiny-idi.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-iw-1.in > tests/tiny-iw-1.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-iw-2.in > tests/tiny-iw-2.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-is-1.in > tests/tiny-is-1.std
	java -cp build/classes/ ce326.hw1.HW1 < tests/tiny-is-2.in > tests/tiny-is-2.std

clean:
	-rm tests/*.out

