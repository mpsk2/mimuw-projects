(*Zadanie polega na napisaniu funkcji do skladania okrągłej i prostokątniej 
kartki wzdłuż prostych i zwracaniu ilości warstw w punkie*)

open List;;

(** Punkt na płaszczyźnie *)
type point = float * float;;

module Matma =
	struct
		let (eps:float) = 1e-8 (*bardzo mała liczba*)
		let fSign (f:float) = if f = 0. then 0. else if f>0. then 1. else -1.
		let fAbs a = if a >= 0. then a else -.a
		let fKw (f:float) = f*.f (*Kwadrat liczby*)
		let fdAB (p1:point) (p2:point) = (*Odległość między punktami*)
			let (x1,y1)=p1 and (x2,y2)=p2 
			in sqrt (fKw (x1-.x2)+. fKw(y1-.y2))
		let fWyznacznik (p1:point) (p2:point) (p3:point) = (*Wyznacznik 3 punktow*)
			let (x1,y1)=p1 and (x2,y2)=p2 and (x3,y3)=p3
			in x1*.(y2-.y3)+.x2*.(y3-.y1)+.x3*.(y1-.y2)
		let fWystarczajoce (f:float) = (*sprawdza czy liczba jest wystarczajoco duza, czy mieści się w niepewności*) 
			if f *. fSign f < eps then 0. else f
		let fdABpC (p1:point) (p2:point) (p3:point) = (*Odleglość punktu od prostej i storna po której jest punkt*)
			let det = fWyznacznik p1 p2 p3
			in (fAbs (det) /. fdAB p1 p2, fSign (fWystarczajoce det)) (*ujemna to prawo, dodatnio lewo, zero to na prostej*)
		let pOdbicie (p1:point) (p2:point) (p:point) (odl:float)= (*w rzeczywistości przesunięcie o wektor*)
			let dl = fdAB p1 p2 and (x1,y1)=p1 and (x2,y2)=p2 and (x,y)=p
			in let x1 = (2. *. (y2-.y1) *. odl)/.dl and y1 = (2. *. (x1-.x2) *. odl)/.dl
			in ((x+.x1,y+.y1):point)
	end;;

	
(** Poskładana kartka: ile razy kartkę przebije szpilka wbita w danym
punkcie *)
type kartka = point -> int;;

(** [prostokat p1 p2] zwraca kartkę, reprezentującą domknięty
prostokąt o bokach równoległych do osi układu współrzędnych i lewym
dolnym rogu [p1] a prawym górnym [p2]. Punkt [p1] musi więc być
nieostro na lewo i w dół od punktu [p2]. Gdy w kartkę tę wbije się 
szpilkę wewnątrz (lub na krawędziach) prostokąta, kartka zostanie
przebita 1 raz, w pozostałych przypadkach 0 razy *)
let prostokat (p1:point) (p2:point) =
	(function (p3:point) ->
		let (x1,y1)=p1 and (x2,y2)=p2 and (x3,y3)=p3
		in if (x1<=x3) && (x2>=x3) && (y1<=y3) && (y2>=y3) then 1 else 0:kartka);;

(** [kolko p r] zwraca kartkę, reprezentującą kółko domknięte o środku
w punkcie [p] i promieniu [r] *)		
let kolko (s:point) (r:float) =
	(function (p:point) ->
		if Matma.fdAB s p <= Matma.fKw r then 1 else 0:kartka);;

let zloz (p1:point) (p2:point) (k:kartka)=
	(function (p:point) -> 
		let odl = Matma.fdABpC p1 p2 p
		in match (snd odl) with
		-1.->0|
		0.->k p|
		1.->k p + k (Matma.pOdbicie p1 p2 p (fst odl))
		:kartka);;
(** [zloz p1 p2 k] składa kartkę [k] wzdłuż prostej przechodzącej
przez punkty [p1] i [p2] (muszą to być różne punkty). Papier jest
składany w ten sposób, że z prawej strony prostej (patrząc w kierunku
od [p1] do [p2]) jest przekładany na lewą. Wynikiem funkcji jest
złożona kartka. Jej przebicie po prawej stronie prostej powinno więc
zwrócić 0. Przebicie dokładnie na prostej powinno zwrócić tyle samo,
co przebicie kartki przed złożeniem. Po stronie lewej - tyle co przed
złożeniem plus przebicie rozłożonej kartki w punkcie, który nałożył
się na punkt przebicia. *)

let skladaj (l: (point * point) list) (k:kartka) =
	fold_left (fun a (p1,p2) -> zloz p1 p2 a) k l;;
(** [skladaj [(p1_1,p2_1);...;(p1_n,p2_n)] k = zloz p1_n p2_n (zloz ... (zloz p1_1 p2_1 k)...)] 
czyli wynikiem jest złożenie kartki [k] kolejno wzdłuż wszystkich prostych 
z listy *)
