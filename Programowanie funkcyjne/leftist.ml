(*type 'a queue*)
(** Typ złączalnej kolejki priorytetowej *)
(*Drzewa lewicowe to kopce binarne (czyli każdy węzeł może mieć 0, 1 lub dwóch potomków) spełniające, oprócz warunku kopca, tzw. warunek lewicowości. 
Warunek lewicowości mówi, że dla każdego węzła skrajnie prawa ścieżka zaczynająca się w danym węźle jest najkrótszą ścieżką od tego węzła do liścia.*)

type 'a queue = 
	Puste |
	Wezel of ('a*int) * 'a queue * 'a queue;; (*po koleji: wartość, wysokość, lewy, prawy*)

(*val empty : 'a queue*)
(** Pusta kolejka priorytetowa *)

let (empty:'a queue) = Puste;;

(*val join : 'a queue -> 'a queue -> 'a queue*)
(** [join q1 q2] zwraca złączenie kolejek [q1] i [q2] *)

let wysokosc (d:'a queue) =(*liczy wysokość skrajnie prawą*)
		match d with 
		    Puste -> 0|
		    Wezel ((_,n),_,_) -> n;;
		    
let rec join (k1:'a queue) (k2:'a queue) =
	match (k1,k2) with
		(Puste,_) -> k2 |
		(_,Puste) -> k1 |
		(Wezel ((a,n),l,p), Wezel ((b,m),_, _)) -> 
			if a<=b then
			    let m = join p k2
			    in let wl = wysokosc l and wm = wysokosc m
			    in 	if (wl > wm) then Wezel ((a,wm+1),l,m)
				else Wezel ((a,wl+1),m,l)
			else join k2 k1;; 

(*val add : 'a -> 'a queue -> 'a queue*)
(** [add e q] zwraca kolejkę powstałą z dołączenia elementu [e] 
    do kolejki [q] *)
    
let add e (q:'a queue) =
	let (pom:'a queue)=Wezel ((e,1),Puste,Puste)
	in join pom q;;

(*exception Empty*)
(** Wyjątek podnoszony przez [delete_min] gdy kolejka jest pusta *)

exception Empty;;

(*val delete_min : 'a queue -> 'a * 'a queue*)
(** Dla niepustej kolejki [q], [delete_min q] zwraca parę [(e,q')] gdzie [e]
    jest elementem minimalnym kolejki [q] a [q'] to [q] bez elementu [e].
    Jeśli [q] jest puste podnosi wyjątek [Empty]. *)
    
let delete_min (q:'a queue) = 
	match q with
		Puste -> raise Empty |
		Wezel ((a,n),l,p) -> (a, join l p);;

(*val is_empty : 'a queue -> bool*)
(** Zwraca [true] jeśli dana kolejka jest pusta. W przeciwnym razie [false] *)

let is_empty (kolejka:'a queue) = 
	(kolejka=Puste);;