int -> int
double -> double
relop -> (<|>|<=|>=|=|!=)
ws -> [ \t\n]
letter_ul -> [A-Za-z_]
digit -> [0-9]
id -> {letter_ul} ({letter_ul} | {digit})*
type -> ({int}|{double})
dec -> {type}({ws}+{id})*;
inner -> ({int}|{double}|({letter_ul} | {digit})*)