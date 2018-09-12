# Run

`java -classpath .:gson-2.8.4.jar GClassifier input1.json input2.json input3.json input4.json input5.json input6.json`

You can find `gson-2.8.4.jar` in `lib` directory

# Output

~~~
input1.json
verdict : type 0 language, PSG (Phrase Structure Grammar)
G[E] = (Vt, Vn, P, E)
Vt : [0, 1, 2, 3, 4, 5, ε, 6, 7, 8, 9]
Vn : [D, E, N]
P  : [ED::=ND|D, E::=EED, D::=0|1|2|3|4|5|6|7|8|9|ε]

input2.json
verdict : type 1 language, CSG (Context Sensitive Grammar)
G[E] = (Vt, Vn, P, E)
Vt : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
Vn : [D, E, N]
P  : [ED::=ND|DDD, E::=EED, D::=0|1|2|3|4|5|6|7|8|9]

input3.json
verdict : type 2 language, CFG (Context Free Grammar)
G[N] = (Vt, Vn, P, N)
Vt : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
Vn : [D, N]
P  : [N::=ND|DDDD, D::=0|1|2|3|4|5|6|7|8|9]

input4.json
verdict : type 2 language, extend CFG (Context Free Grammar)
G[N] = (Vt, Vn, P, N)
Vt : [0, 1, 2, 3, 4, 5, ε, 6, 7, 8, 9]
Vn : [D, N]
P  : [N::=ND|DDDD, D::=0|1|2|3|4|5|6|7|8|9|ε]

input5.json
verdict : type 3 language, RG (Regular Grammar)
G[N] = (Vt, Vn, P, N)
Vt : [0, 1, 2, 3, 4, 5, 6, 7, x, 8, 9]
Vn : [D, N]
P  : [N::=Dx|x, D::=0|1|2|3|4|5|6|7|8|9]

input6.json
verdict : type 3 language, extend RG (Regular Grammar)
G[N] = (Vt, Vn, P, N)
Vt : [0, 1, 2, 3, 4, 5, ε, 6, 7, x, 8, 9]
Vn : [D, N]
P  : [N::=Dx|x, D::=0|1|2|3|4|5|6|7|8|9|ε]
~~~
