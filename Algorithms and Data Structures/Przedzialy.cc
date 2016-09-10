#include <iostream>

#define null 0

typedef unsigned long long T;

#define max_rozmiar (1000 * 1000 + 1)

struct wezel{
	T pocz;
	T kon;
	T wys;
	T w_lewym;
	T w_mnie;
	T w_prawym;
	wezel* lewe;
	wezel* prawe;
	wezel(
		T _pocz, 
		T _kon){
		
		pocz = _pocz;
		kon = _kon;
		wys = 1;
		w_lewym = 0;
		w_mnie = ((kon - pocz + 2) * (kon - pocz + 1)) >> 1;
		w_prawym = 0;
		lewe = null;
		prawe = null;
	}
};

char tab[max_rozmiar][sizeof(wezel)];
int current_i = -1;

struct wynik_wyszukiwania {
	wezel *w;
	T ktore;
};

inline int wys(wezel *p) {
	return p ? p->wys : 0;
}

inline int bfactor(wezel *p) {
	return wys(p->prawe) - wys(p->lewe);
}

inline void pop_wys(wezel *p) {
	T pom1, pom2;
	pom1 = wys(p->lewe);
	pom2 = wys(p->prawe);
	p->wys = (pom1>pom2 ? pom1 : pom2) + 1;
}

inline T w_drzewie(wezel *p) {
	return p ? p->w_lewym + p->w_mnie + p->w_prawym : 0;
}

inline void pop_w_drzewie(wezel *p) {
	p->w_lewym = w_drzewie(p->lewe);
	p->w_mnie = ((p->kon - p->pocz + 2) * (p->kon - p->pocz + 1)) >> 1;
	p->w_prawym = w_drzewie(p->prawe);
}

wezel* obroc_prawo(wezel *p) {
	wezel* q = p->lewe;
	p->lewe = q->prawe;
	q->prawe = p;
	pop_wys(p);
	pop_wys(q);
	pop_w_drzewie(p);
	pop_w_drzewie(q);
	return q;
}

wezel* obroc_lewo(wezel* q) {
	wezel* p = q->prawe;
	q->prawe = p->lewe;
	p->lewe = q;
	pop_wys(q);
	pop_wys(p);
	pop_w_drzewie(q);
	pop_w_drzewie(p);
	return p;
}

wezel* zbalansuj(wezel* p) {
	pop_wys(p);
	pop_w_drzewie(p);
	if(bfactor(p) == 2){
		if(bfactor(p->prawe) < 0)
			p->prawe = obroc_prawo(p->prawe);
		return obroc_lewo(p);
	} else if(bfactor(p) == -2) {
		if(bfactor(p->lewe) > 0)
			p->lewe = obroc_lewo(p->lewe);
		return obroc_prawo(p);
	}
	
	return p;
}

wezel* wstaw(wezel* p, T pocz, T kon) {
	if(p == null)
		return new (tab[++current_i]) wezel(pocz, kon);
	
	if(pocz < p->pocz)
		p->lewe = wstaw(p->lewe, pocz, kon);
	
	else
		p->prawe = wstaw(p->prawe, pocz, kon);
	
	return zbalansuj(p);
}

wezel* znajdz_min(wezel* p) {
	return p->lewe ? znajdz_min(p->lewe) : p;
}

wezel* usun_min(wezel* p) {
	if(p->lewe == null)
		return p->prawe;
	p->lewe = usun_min(p->lewe);
	return zbalansuj(p);
}

wezel* usun(wezel* p, T k) {
	if(w_drzewie(p->lewe) >= k){;
		p->lewe = usun(p->lewe, k);}
	else if(w_drzewie(p->lewe) + p->w_mnie < k){
		p->prawe = usun(p->prawe, k - p->w_mnie - w_drzewie(p->lewe));}
	else{
		wezel* q = p->lewe;
		wezel* r = p->prawe;
		
		if(!r)
			return q;
		
		wezel* min = znajdz_min(r);
		min->prawe = usun_min(r);
		min->lewe = q;
		return zbalansuj(min);
	}
	return zbalansuj(p);
}

wezel* zmien(wezel* p, T pocz, T pnew, T knew) {
	
	if(pocz < p->pocz)
		p->lewe = zmien(p->lewe, pocz, pnew, knew);
	else if(pocz == p->pocz){
		p->pocz = pnew;
		p->kon = knew;
	} else
		p->prawe = zmien(p->prawe, pocz, pnew, knew);
	
	return zbalansuj(p);
}

wezel* podziel_na_2(wezel* p, T pocz, T knew1, T pnew2) {
	if(pocz < p->pocz)
		p->lewe = podziel_na_2(p->lewe, pocz, knew1, pnew2);
	else if(pocz == p->pocz){
		T pom = p->kon;
		p->kon = knew1;
		p->w_mnie = ((p->kon - p->pocz + 2) * (p->kon - p->pocz + 1)) >> 1;
		p = wstaw(p, pnew2, pom);
	} else
		p->prawe = podziel_na_2(p->prawe, pocz, knew1, pnew2);
		
	return zbalansuj(p);
}

struct przedzial {
	T pocz;
	T kon;
	inline bool operator==(przedzial const& prawy) { return (pocz == prawy.pocz) & (kon == prawy.kon); }
	inline bool operator!=(przedzial const& prawy) { return !(*this == prawy); }
};

constexpr inline T koniec(T n, T k) {
	return (k * (n + (n-k+1))) >> 1;
}
constexpr inline T poczatek(T n, T k) {
		return koniec(n, k-1) + 1;
}

przedzial rozloz(wynik_wyszukiwania w) {
	przedzial prz;
	
	T p = 1;
	T n = w.w->kon - w.w->pocz + 1;
	T k = n;
	T s;
	T pom;
	while(p < k){
		s = (p + k + 1) >> 1;
		pom = poczatek(n, s);
		if(pom > w.ktore)
			k = s - 1;
		else
			p = s;
	}
	
	prz.pocz = p + w.w->pocz - 1;
	prz.kon = prz.pocz + w.ktore - poczatek(n, p);
	
	return prz;
}


#include <stdio.h>

wezel* inne_rozwiazanie(wezel* p, T k) {	
				
	if(w_drzewie(p->lewe) >= k){
		p->lewe = inne_rozwiazanie(p->lewe, k);
	}
	else{
			T pom = w_drzewie(p->lewe) + p->w_mnie;
			if(k <= pom) {
				wynik_wyszukiwania wynik;
				wynik.ktore = k - w_drzewie(p->lewe);
				wynik.w = p;
				
				przedzial prz = rozloz(wynik);
		
				T a, b, c;
				
				printf("%llu %llu\n", prz.pocz, prz.kon);
				
				if((prz.pocz == wynik.w->pocz) && (prz.kon == wynik.w->kon)){
					p = usun(p, w_drzewie(p->lewe) + 1);
					if(!p)
						return null;
				} else if(prz.pocz == wynik.w->pocz) {
					a = prz.kon + 1;
					b = wynik.w->kon;
					p = zmien(p, wynik.w->pocz, a, b);
				} else if(prz.kon == wynik.w->kon) {
					a = wynik.w->pocz;
					b = prz.pocz - 1;
					p = zmien(p, wynik.w->pocz, a, b);
				} else {
					b = prz.pocz - 1;
					c = prz.kon + 1;
					p = podziel_na_2(p, wynik.w->pocz, b, c);
				}
				
			} else {
				p->prawe = inne_rozwiazanie(p->prawe, k - pom);
			}
	}
	
	return zbalansuj(p);
}


int main() {
	unsigned long long n;
	
	scanf("%llu", &n);
	wezel *w = new (tab[++current_i]) wezel(1, n);	
	
	unsigned long long p;
	for(;;) {
		printf("%llu\n", w_drzewie(w));
		scanf("%llu", &p);
		
		if(p == -1)
			break;
		
		p++;
		
		w = inne_rozwiazanie(w, p);
	}
	
	return 0;
}