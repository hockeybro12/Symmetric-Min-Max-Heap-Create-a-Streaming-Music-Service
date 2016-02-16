# Symmetric-Min-Max-Heap-Create-a-Streaming-Music-Service

This is a streaming music service. Add songs using the "d B" command, as explained below. Song information is stored in a symmetric min max 
heap.  

Commands with explanations  
d S N L     At date d, song S has been listened to additional ΔN times and liked ΔL times. You update Ns and Ls adding the respective quantities. Note that ΔN and ΔL can be negative, indicating a drop in popularity. In that case you subtract, but Ns and Ls must not become negative. Update the popularity !" according to the formula !" = #" + 2$" where #" and $" are new values.  

d T3        At date d, report the 3 top songs with the highest popularity. Order songs of equal popularity by acquisition date, with older songs listed before newer ones.  

d B         At date d, buy a new song with initial N=20 and L=20. The song gets the title Sd, where d is the date  

d X n       At date d, delete n songs with the lowest priority. If two songs () and (* have equal popularity but only one is to be deleted, you delete the older one. That is, with d1 and d2 the respective acquisition dates, () is deleted if d1<d2, and (* is deleted when d2<d1.  

d end       Print the highest and lowest popularity, then stop the program
