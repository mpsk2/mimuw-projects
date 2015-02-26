(*Plik realizuje drzewa AVL rozłącznych przedziałów liczb całkowitych, wiele funkcji zostało przepisanych z orginalnej biblioteki
  dostarczonej do zadania p.Set.ml, a całość jest realizowana wg. specyfikacji iSet.ml*)

type set =
	|Empty
	|Node of set * (int*int) * set * int * int  (*Po koleji: lewe poddrzewo, przedział, prawe poddrzewo, wysokość
						liczba elementów*)
						
type t = { set : set; } (*Zrezygnuję z funkcji, ponieważ tu będzie tylko jedna*)

let empty =  { set=Empty } (*Pusty element*)

let is_empty x = (x.set=Empty) (*sprawdza czy element jest pusty*)

let height = function (*funkcja wysokość zwraca wysokość drzewa*)
	|Empty -> 0
	|Node (_,_,_,h,_) -> h
	
let elementy = function (*funkcja zwraca ilość elementów w drzewo, coś ala height dostarczone do zadania*)
	|Empty -> 0
	|Node (_,_,_,_,e) -> e
	
let sumaElem a b = (*funkcja zwraca sumę 2 liczb, wg.  def. funkcji below*)
	if max_int - a < b then max_int
	else a+b
	
let dlPrzed (a,b) = (*zwraca długość przedziału*)
	if b-a+1 <= 0 then max_int
	else b-a+1
	
let make l w p = (*Zwraca na siłę stworzone drzewo z lewe, wartości i prawego*)
	Node (l,w,p, max (height l) (height p) + 1, sumaElem (sumaElem (elementy l) (elementy p)) (dlPrzed w))
	
let bal l k r = (*Funckja wyrównująca drzewo, wykonująca do 2 obrotów, skopiowana z pSet.ml, bez zmian*)
  let hl = height l in
  let hr = height r in
  if hl > hr + 2 then
    match l with
    | Node (ll, lk, lr, _, _) ->
        if height ll >= height lr then make ll lk (make lr k r)
        else
          (match lr with
          | Node (lrl, lrk, lrr, _, _) ->
              make (make ll lk lrl) lrk (make lrr k r)
          | Empty -> assert false)
    | Empty -> assert false
  else if hr > hl + 2 then
    match r with
    | Node (rl, rk, rr, _, _) ->
        if height rr >= height rl then make (make l k rl) rk rr
        else
          (match rl with
          | Node (rll, rlk, rlr, _, _) ->
              make (make l k rll) rlk (make rlr rk rr)
          | Empty -> assert false)
    | Empty -> assert false
  else Node (l, k, r, max hl hr + 1, sumaElem (sumaElem (elementy l) (elementy r)) (dlPrzed k) )
  
let rec min_elt = function (*Zwraca najmniejszy element*)
  | Node (Empty, k, _, _,_) -> k
  | Node (l, _, _, _,_) -> min_elt l
  | Empty -> raise Not_found

let rec remove_min_elt = function (*Zwraca drzewo pozbawione najmniejszego elementu*)
  | Node (Empty, _, r, _,_) -> r
  | Node (l, k, r, _,_) -> bal (remove_min_elt l) k r
  | Empty -> invalid_arg "iSet.remove_min_elt"
  
let rec max_elt = function (*Zwraca największy element*)
	| Node (_,k,Empty,_,_) -> k
	| Node (_,_,p,_,_) -> max_elt p
	| Empty -> raise Not_found
	
let rec remove_max_elt = function (*Usuwa największy element*)
	| Node (l,_,Empty,_,_) -> l
	| Node (l,k,p,_,_) -> bal l k (remove_max_elt p)
	| Empty -> invalid_arg "iSet.remove_max_elt"
  
let merge t1 t2 = (*Łączy 2 dzewa avl w jedno przy założeniu, że pierwsze jest ściśle mniejsze od drugiego*)
  match t1, t2 with
  | Empty, _ -> t2
  | _, Empty -> t1
  | _ ->
      let k = min_elt t2 in
      bal t1 k (remove_min_elt t2)
      
let porownaj (a,b) (c,d) = (*Porównuje przedziały, czy pierwszy jest większy od drugiego, czy nie (przy założeniu, że nie jest tak
			że trzeba je scalić, mimo że są rozłączne*)
	if a>d then 1
	else if c>b then -1
	else 0 (*W jakiś sposób się zawiera*)
	
let zawiera (a,b) c = (*Sprawdza, czy dany przedział (a,b) zawiera liczbę c*)
	((a<=c) && (b>=c))

let rec add_one cmp x = function (*Funkcja dodaje element do drzewa, przy optymistycznym założeniu, że nie trzeba nic scalać*)
  | Node (l, k, r, h, w) ->
      let c = cmp x k in
      if c = 0 then Node (l, x, r, h, w)
      else if c < 0 then
        let nl = add_one cmp x l in
        bal nl k r
      else
        let nr = add_one cmp x r in
        bal l k nr
  | Empty -> Node (Empty, x, Empty, 1, dlPrzed x)
  
let iter f { set = set } = (*Funkcja wykonuje funkcję f dla każdego przedziału w drzewi*)
  let rec loop = function
    | Empty -> ()
    | Node (l, k, r, _, _) -> loop l; f k; loop r in
  loop set

let fold f { set = set } acc = (*Funkcja fold dla tego drzewa*)
  let rec loop acc = function
    | Empty -> acc
    | Node (l, k, r, _, _) ->
          loop (f k (loop acc l)) r in
  loop acc set

let elements { set = set } = (*Funckja wypisuje wszystkie rozłączne przedziały tego drzewa*)
  let rec loop acc = function
      Empty -> acc
    | Node(l, k, r, _, _) -> loop (k :: loop acc r) l in
  loop [] set
  
let mem x { set = set } =
  let rec loop = function
    | Node (l, k, r, _, _) ->
        let c = porownaj (x,x) k in
        c = 0 || loop (if c < 0 then l else r)
    | Empty -> false in
  loop set

let exists = mem

let below x { set = set } = (*Funkcja zwraca ilość elementów w drzewie niewiększych niż x lub max_int, jeżeli ta liczba miała 
	przekroczyć max_int*)
	let rec loop = function
		| Empty -> 0
		| Node (l,(a,b),r,_,liczba) -> 
			let c = porownaj (x,x) (a,b) in
				if c>0 then sumaElem (loop r)  (sumaElem (dlPrzed (a,b)) (elementy l))
				else if c<0 then loop l
				else sumaElem (elementy l) (dlPrzed (a,x))
	in loop set
	
let rec join cmp l v r = (*Łączy 2 rozłączne poddrzewa, tak że jedno jest ściśle mniejsze od drugiego, *)
  match (l, r) with
    (Empty, _) -> add_one cmp v r
  | (_, Empty) -> add_one cmp v l
  | (Node(ll, lv, lr, lh, _), Node(rl, rv, rr, rh, _)) ->
      if lh > rh + 2 then bal ll lv (join cmp lr v r) else
      if rh > lh + 2 then bal (join cmp l v rl) rv rr else
      make l v r
       
let split a { set = set } = (*Rozdziela drzewo wg. danego elementu liczbowego*)
	let rec loop = function 
		|Empty -> Empty,false,Empty
		|Node (lewe, (x,y), prawe, _, _) ->
			let c = porownaj (a,a) (x,y) in 
				if c = 0 then (if a = x then lewe else join porownaj lewe (x,a-1) Empty),true,
						(if a = y then prawe else join porownaj Empty (a+1,y) prawe)
				else if c < 0 then
					let (ll, pres, rl) = loop lewe in (ll, pres, join porownaj rl (x,y) prawe)
				else
					let (lr, pres, rr) = loop prawe in (join porownaj lewe (x,y) lr, pres, rr)
	in let lewe_nowe , warunek , prawe_nowe = loop set in
	{ set = lewe_nowe } , warunek , { set = prawe_nowe }
	
let remove (a,b) { set = set } = (*Funkcja która usuwa dany przedział z drzewa*)
	let { set = ls }, wl, lp = split a { set = set } and pl, wp, { set = ps } = split b { set = set } in
		if ps = Empty then { set = ls }
		else let k = min_elt ps
		in {set = join porownaj ls k (remove_min_elt ps) }
		
let add (a,b) { set = set } = (*Dodaje dany przedział do drzewa *)
	let c = if a = (-max_int) - 1 then a else a-1 and d = if b = max_int then max_int else b+1 in
	let { set = ll }, wl, { set = pl } = split a { set = set } and { set= lp }, wp, { set = pp } = split b { set = set } in
	match ll,pp with (*Wykona operacje w kolejności, sprawdzi czy dane poddrzewo jest puste, jeżeli tak, to czy trzeba
			przedział sklejić z czymś tam występującym, jeżeli tak to z tym sklejji, a jak nie to po prostu złączy
			poddrzewa z przedziałem*)
		|Empty,Empty -> { set = add_one porownaj (a,b) Empty }
		|_,Empty -> let max_ll = max_elt ll in
			if mem c { set = ll } then 
				let e = fst max_ll in 
					{ set = add_one porownaj (e,b) (remove_max_elt ll) }
			else 
				{ set = add_one porownaj (a,b) ll }
		|Empty,_ -> let min_pp = min_elt pp in
			if mem d { set = pp } then 
				let e = snd min_pp in
					{ set = add_one porownaj (a,e) (remove_min_elt pp) }
			else
				{ set = add_one porownaj (a,b) pp }
		|_,_ ->  let min_pp = min_elt pp and max_ll = max_elt ll in
			match mem c { set = ll }, mem d { set = pp } with
				|false,false -> { set = join porownaj ll (a,b) pp }
				|_,false -> let e = fst max_ll in 
						{ set = join porownaj (remove_max_elt ll) (e,b) pp }
				|false,_ -> let e = snd min_pp in 
						{ set = join porownaj ll (a,e) (remove_min_elt pp) }
				|_,_ -> let e = fst max_ll and f = snd min_pp in 
						{ set = join porownaj (remove_max_elt ll) (e,f) (remove_min_elt pp) }