# Tema2 - Analiza algoritmilor
## Dumitru Elena Bianca - 324CD

## Pregatirea scheletului pentru rezolvarea temei
La task-urile 1 si 2, trebuie pe prima linie din fisierul de out scris True/False. <br />

Pentru a putea opera cu functiile din schelet, am modificat antetul lor
pentru a avea acces la fisierele de care avem nevoie. Deci, vom avea asocierile: <br />
	- readProblemData -> String inFilename <br />
	- formulateOracleQuestion -> String oracleInFilename <br />
	- decipherOracleAnswer -> String oracleOutFilename <br />
	- writeAnswer -> String outFilename <br />
	- solve -> va primi toti cei 4 parametri in ordinea de mai sus.


## Task1

### Functia readProblemData

In aceasta funtie am citit datele din fisier, folosind BufferedReader, o clasa deja existenta in Java, recomandata si in cerinta. Am citit linie cu linie
si acolo unde aveam mai multe date -> relatiile dintre familii (practic muchiile
din graf), am pus rezultatul intr-un vector "relations", cu ajutorul caruia am
construit graful. Acesta este orientat, pentru a evita duplicarea clauzelor in
continuare.

### Functia formulateOracleQuestion

Aceasta functie se ocupa de formatarea fisierului care va fi transmis 
mai departe oracolului. Observam din enuntul cerintei ca rezolvarea poate fi in-
teleasa ca o reducere a problemei k-color la SAT. Aici spionii reprezinta culo-
rile, iar k este numarul acestora. Nodurile grafului suport sunt de fapt famili-
ile, iar relatiile dintre acestea sunt nodurile. <br />

Am gandit clauzele in felul urmator: <br />
Variabilele care trebuie sa reprezinte starile trebuie sa respecte inde-
xarea incepand cu 1. Astfel, vom asocia cate un grup de k variabile fiecarei fa-
milii. <br />

De exemplu, pentru 2 familii si 3 spioni, prima familie se va identifica
prin variabilele 1, 2, 3, iar a doua familie prin 4, 5, 6. Variabilele 1 si 4
reprezinta asignarea spionului 1 (din cei 3) ambelor familii.
ex: 2 -> fam 1 are spionul 2 <br />
    6 -> fam 2 are spionul 3 <br />
Astfel, numarul total de variabile va fi numarul familiilor * numarul 
spionilor, respectiv "verticesNo" si "spiesNo" din implementare. <br />

Pentru a calcula numarul de clauze, trebuie sa vedem mai intai cum le 
compunem. Voi folosi ca referinta exemplul de mai sus: <br />
	1. O familie trebuie sa aiba asignat un spion, asadar dintre 1, 2 si 3
	   o variabila trebuie sa fie adevarata -> avem clauza 1 2 3 <br />
	2. O familie nu poate avea mai mult de un spion, astfel nu putem avea
	   mai mult de o variabila adevarata, deci nu putem avea simultan 1, 2,
	   respectiv 1, 3 sau 2, 3. Pentru a rezolva acest lucru trebuie sa avem
	   clauze de forma "not(1 and 2)" sau "-1 or -2" -> "-1 -2". Procedam
	   analog pentru celelalte combinatii. <br />
	Clauzele de mai sus se repeta pentru variabilele fiecarui nod / familii. <br />
	3. Acum trebuie sa luam in considerare unde avem muchii in graf; 2 no-
	   duri vecine nu pot avea aceeasi culoare. Asadar, daca as avea o relaie intre familiile 1 si 2, ar trebui sa avem clauzele de forma:
	   "not(1 and 4)", "not(2 and 5)", care resping ideea asignarii aceluiasi spioni unor familii care se inteleg. <br />

Deci, pentru "1." vom avea "verticesNo" clauze, cate una pentru existenta fiecarei familii. Pentru "2." vom avea combinari de k luate cate 2 ori numa-
rul de noduri, adica "(spiesNo * (spiesNo - 1)) / 2 * verticesNo", iar pentru 
"3.", numarul de muchii * numarul de spioni. <br />

Aceasta este logica din spatele clauzelor. Pentru a intelege mai bine 
cum trebuie scrise acestea, intr-un mod cat mai organizat si eficient, am urmarit acest videoclip: https://www.youtube.com/watch?v=HhFSgXbWiWY . <br />


### Functia decipherOracleAnswer

In aceasta functie, am citit rezultatul din fisierul raspuns. Daca ras-
punsul era "False", am populat campul clasei Task1 value cu valoarea false, pen-
tru a fi prelucrat mai departe in output-ul final. Daca raspunsul era "True", am
actualizat variabila value si am interpretat raspunsul. <br />

Cum am explicat mai sus, pozitia variabilei pozitive din fiecare grup de
k variabile, reprezenta numarul spionului asignat familiei respective. Astfel, 
am construit un vector de spioni, in care am notat in ordinea indexarii famili-
lor spionul corespunzator "spiesList".

### Functia writeAnswer

In aceasta functie m-am folosit de clasa BufferedWriter pentru a scrie
raspunsul in outFilename. Daca era "False" -> notam false. Altfel, printam"True"
si afisam pe urmatoarea linie vectorul de spioni.


## Task2


### Functia readProblemData

Este aproape identica cu cea de la Task1, cu mentiunea ca am schimbat
numele variabilelor, din spioni in clica, pentru ca aceasta cerinta propune re-
ducerea polinomiala a problemei k-clica la SAT; k este analogul lui K din fisi-
erul de intrare, reprezentand dimensiunea familiei extinse cautate.

### Functia formulateOracleQuestion

La aceasta cerintele, clauzele au fost putin mai complicate decat la 
prima. Pentru o rezolvare eficienta, lipsita de clauze redundante, mi-am bazat
rezolvarea pe pergamentul "Lost Paper" atasat in cerinta. <br />
Dupa cum am semnalat in comentariile din rezolvare, am urmarit punctele
de acolo, adica b si cele 2 conditii de la c. <br />
	
1. Am inceput cu conditia c.1, deoarece aceasta se regaseste si in im-
   plementarea de la taskul 1: adica, un nod nu poate ocupa simultan
   pozitia i si j din clica. Analog, o familie nu poate avea asignata
   mai multi spioni. Pentru a rezolva aceasta problema clauzele sunt de
   forma "not(1 and 2)" adica "not 1 or not 2" -> "-1 -2" (conform exem-
   plului de la task1). Bineinteles, nu putem avea o clica de 3 doar din
   2 familii, insa exemplul are rol pur teoretic si demonstrativ.
   Avem in total combinari de k luate cate 2 ori nr de noduri, adica 		   "(cliqueNo * (cliqueNo - 1)) / 2 * verticesNo".
2. Urmatoarea conditie care trebuie respectata este cea de la b, care 
   atesta ca daca 2 noduri nu au o muchie in comun, acestea nu pot sa 
   faca parte simultan din clica.
   Am transformat clauzele de la task1 care se bazau pe existenta mu-
   chiilor astfel: bazandu-ma pe un vector care contine lista de adia-
   centa a fiecarui nod, am construit un vector care contine fix noduri-
   le cu care nu se invecineaza fiecare nod. Deci, pentru fiecare nod, 
   trebuie sa construim o clauza de respingere fata de celalalt nod pen-
   tru toate starile posibile ale acestora.
   Asadar, de exemplu, daca intre nodurile 1 si 2 nu exista muchie si 
   acestea au anexate varibilele 1, 2, 3, respectiv 4, 5, 6 pentru o 
   ca de dimensiune 3, va trebui sa scriem o clauza de forma "not(1 and
   4)" <=> "not 1 or not 4" <=> "-1 -4" pentru fiecare combinatie a va-
   riabilelor: 1 cu 4, 5, 6; 2 cu 4, 5, 6 si 3 cu 4, 5, 6.
   Numarul de clauze va fi: combinari de nr de noduri luate cate 2 - nu-
   marul de muchii * dimensiunea clicii^2.
3. Mai trebuie respectata conditia c.2, adica 2 noduri diferite nu pot
   fi simultan index-ul i din clica. Deci, pentru fiecare codificare
   asociata pozitiei i dintre variabile, una si numai una trebuie sa fie
   adevarata / pozitiva. Pentru 3 familii si o clica de 3, trebuie sa 
   avem o singura variabila adevarata dintre 1, 4, 7. Analog pentru 
   2, 5, 8 si 3, 6, 9. Pentru a obtine acest lucru, procedam analog ca
   mai sus: clauze de tipul "-1 -4" si o clauza completa de "1 4 7" 
   pentru a ne asigura ca avem un ocupant al pozitiei i (aici - 1).
   In total, vor fi cliqueNo * verticesNo * (verticesNo - 1) / 2 pentru
   primele conditii si cliqueNo clauze pentru variabilele pozitive.


## Task3


### Functia readProblemData

Citirea datelor din fisier, precum si retinerea variabilelor are loc in
mod asemanator cu celelalte cerinte; insa, de aceasta data, primim doar graful,
neavand informatia k. <br />

Functia principala din acest task este solve care se ocupa de fapt cu 
reducerea acestui task la task2. Pentru a putea transmite informatiile mai de-
parte ca sa fie prelucrate de task2, trebuie sa rescrim formatul fisierul de 
input. Deci, va trebui sa adaugam un numar k care reprezinta dimensiunea de cli-
ca pe care o cautam. Numarul k depinde de reducerea pe care o alegem. <br />

Pentru reducere, am construit un "non-graf" al grafului initial G, com-
plementul acestuia care contine doar muchiile care nu sunt in G. Deci, noul graf
va contine toate nodurile din G, nicio muchie din G si toate muchiile care nu 
exista in G. Daca exista o clica V' in nonG, atunci V - V' este o acoperire a 
lui G. <br />

Asadar, tot ce ne ramane de facut dupa constructia grafului este sa ape-
lam functia reduceToTask2 cu noile date, noul graf si dimensiunea clicii pe care
o dorim. Deoarece vrem sa gasim numarul minim de arestari, vom cauta incepand 
cu 1, asta insemnand o clica de dimensiune "numarul de noduri - 1" si apoi mic-
soram succesiv clica cautata. <br />

### Functia reduceToTask2

Aceasta functie acopera si functionalitatea functiei 
extractAnswerFromTask2 deoarece, in implementarea mea, reducerea la task2 impli-
ca si interpretarea datelor pentru a sti sa oprim iteratiile si, de asemenea, urmeaza o structura mai armonioasa. <br />

Asadar, citim raspunsul interogarii clica din task2 si vom in vectorul "families" familiile sortate crescator in functie de indexul acestora. Vectorul families contine toate familiile care trebuie arestate, in ordinea descoperirii acestora. Acest proces are loc doar daca raspunsul este "True" si avem intr-adevar o clica.
  
### Functia writeAnswer

Are un format asemanator cu echivalentele din celelalte task-uri. Vom
scrie in fisierul de out, folosind BufferedWriter, elementele din vectorul 
"families" reprezentand exact familiile care trebuie arestate.
