CC = g++
CFLAGS = -Wall -Wextra -O2
LDLIBS = -lm

build:
	g++ -Wall -Wextra main.cpp algo1.h -o main
	g++ -Wall -Wextra main.cpp algo2.h -o main2
	g++ -Wall -Wextra main.cpp algo3.h -o main3

tests:
	./main ./tests/Dijkstra/in/test1.in Dijkstra
	./main ./tests/Dijkstra/in/test2.in Dijkstra
	./main ./tests/Dijkstra/in/test3.in Dijkstra
	./main ./tests/Dijkstra/in/test4.in Dijkstra
	./main ./tests/Dijkstra/in/test5.in Dijkstra
	./main ./tests/Dijkstra/in/test6.in Dijkstra
	./main ./tests/Dijkstra/in/test7.in Dijkstra
	./main ./tests/Dijkstra/in/test8.in Dijkstra
	./main ./tests/Dijkstra/in/test9.in Dijkstra
	./main ./tests/Dijkstra/in/test10.in Dijkstra
	./main2 ./tests/BellmanFord/in/test1.in BellmanFord
	#La fel ca la test2 Dijkstra
	./main2 ./tests/BellmanFord/in/test2.in BellmanFord
	./main2 ./tests/BellmanFord/in/test3.in BellmanFord
	./main2 ./tests/BellmanFord/in/test4.in BellmanFord
	./main2 ./tests/BellmanFord/in/test5.in BellmanFord
	./main2 ./tests/BellmanFord/in/test6.in BellmanFord
	./main2 ./tests/BellmanFord/in/test7.in BellmanFord
	#La fel ca la test7 Dijkstra
	./main2 ./tests/BellmanFord/in/test8.in BellmanFord
	./main2 ./tests/BellmanFord/in/test9.in BellmanFord
	./main2 ./tests/BellmanFord/in/test10.in BellmanFord
	#La fel ca la Dijkstra
	./main3 ./tests/Dijkstra2/in/test1.in Dijkstra2
	./main3 ./tests/Dijkstra2/in/test2.in Dijkstra2
	./main3 ./tests/Dijkstra2/in/test3.in Dijkstra2

clean:
	rm -rf main

algo.o: algo1.cpp
	g++ algo1.cpp -c $(CFLAGS)

.PHONY: clean
clean:
	rm -rf *.o
