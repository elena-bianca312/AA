# Tema 1 - Drumuri minime in graf
# Dumitru Elena Bianca 324CD

Fisierul main.cpp se ocupa de citirea datelor din fisierul dat la 
intrare si se ocupa de aplicarea fiecarui algoritm pe testul potrivit in functie
de tipul algoritmului dat in linia de comanda. <br />

Fisierul algo1.h realizeaza implementarea algoritmului Dijkstra. Graful
primit ca parametru este reprezentat de o lista de adiacenta de dimensiunea
(V, V), unde V = numarul de noduri. Astfel, in functia dijkstra avem nevoie de
un vector de distante (dist[]) si un vector de visited (sptSet[]). Pentru 
fiecare nod calculam si imbunatatim distanta de la nodul sursa daca este cazul.
La final, scriem rezultatul in fisierul out transmis ca parametru. Acest 
algoritm poate fi aplicat doar pe grafuri cu costuri nenegative, altfel poate da
un rezultat eronat. <br />

Fisierul algo2.h implementeaza algoritmul BellmanFord. Si aici vom avea
un vector de distante care va fi initializat cu un numar foarte mare. Pentru
fiecare nod si pentru fiecare muchie vom calcula distanta fata de nodul sursa si
o vom imbunatati daca este nevoie. Dupa ce vom termina cele N - 1 repetitii ale
muchiilor, ar trebui sa avem asignat un cost corect pentru fiecare nod. Insa,
daca vom continua repetitiile si costul continua sa se modifice, inseamna ca 
avem un ciclu negativ si vom semnala acest lucru. Vom scrie rezultatlu in 
fisierul de out corespunzator. <br />

Fisierul algo3.h realizeaza implementarea algoritmului Dijkstra adaptat,
pentru muchii cu costuri mici. Retinem costul maxim al muchiilor pe care il vom
da ca parametru functiei. Implementarea este asemanatoare cu Dijkstra normal,
doar ca ne vom folosi de structura bucket pentru a putea retine si opera cu 
costurile de la 1 la W (costul maxim). <br />
	
Pentru rularea executabilului (unul diferit pentru fiecare algoritm in 
parte), trebuie sa scriem in linia de comanda testul din folderul corespunzator
si algoritmul aplicat -> se poate urma modelul din Makefile.
Fisierele de iesire contin distanta de la nodul sursa la
toate celelalte: de la 1 la V, exclunzandu-se pe sine (sursa). <br />

	Resurse: 	
	-> https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-
	greedy-algo-7/ : Dijkstra alg
	-> https://www.infoarena.ro/problema/dijkstra?action=attach-list :
	Dijkstra teste
	-> https://www.geeksforgeeks.org/bellman-ford-algorithm-simple-
	implementation/ : BellmanFord alg
	-> https://www.infoarena.ro/problema/bellmanford?action=attach-list :
	BellmanFord teste
	-> https://www.geeksforgeeks.org/dials-algorithm-optimized-dijkstra-for
	-small-range-weights/ : Dijkstra adaptat - Dial
	Testele pentru Dijkstra adaptat au fost alese din testele Dijkstra,
	fiind selectate doar cele cu costuri mici
