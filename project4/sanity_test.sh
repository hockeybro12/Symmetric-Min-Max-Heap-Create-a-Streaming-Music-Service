javac project4.java
java project4 < ../testcases/TestInput$1.txt > ../testcases/output_$1_data.txt

if cmp -s ../testcases/TestInput$1.txt ../testcases/output$1.txt
then
echo "Sanity Test Passed!"
else
echo "Output Differs"
fi