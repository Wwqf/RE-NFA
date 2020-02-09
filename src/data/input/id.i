letter_ul -> [A-Za-z_]
id -> {letter_ul} ({letter_ul} | {digit})*

int -> int
double -> double
ws -> [ \t\n]
sym -> ,
type -> ( {int} | {double} )
dec -> {type}({ws}+ {id}  {sym}?)*;