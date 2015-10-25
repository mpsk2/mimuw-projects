#include <iostream>
#include <inttypes.h>

typedef int64_t rozmiar;

const rozmiar maxN = 64 * 1024;
const int maxK = 10;
const rozmiar dzielnik = 1000 * 1000 * 1000;
rozmiar TW[maxN];
rozmiar T2[maxN];
rozmiar T[maxN*2][maxK][2]; //0 dla widocznych, 1 dla niewidocznych

void inicjuj(rozmiar n, rozmiar pot){
	rozmiar pocz = pot-1;
        
	for (rozmiar i = pot; i < pot + n; i++)
		T[i][0][1] = 1;
	while(pocz >= 1){
		T[pocz][0][1] = T[pocz*2][0][1] + T[pocz*2+1][0][1];
		if(T[pocz][0][1] > dzielnik)
			T[pocz][0][1] %= dzielnik;
		pocz--;
	}
}

rozmiar wartosc(rozmiar p, rozmiar k, int poz, rozmiar pot){
	rozmiar mp = pot+p;
	rozmiar mk = pot+k; 
	rozmiar wyn = T[mp][poz][0];
	if(mp != mk) wyn+= T[mk][poz][0];
	while(mp/2 != mk/2){
		if(mp % 2 == 0) wyn += T[mp+1][poz][0];
		if(mk % 2 == 1) wyn += T[mk-1][poz][0];
		if(wyn > dzielnik)
			wyn %= dzielnik;
		mp /= 2;
		mk /= 2;
	}
	return wyn;
}
void popraw(rozmiar l, int poz, rozmiar pot){
    
	rozmiar v = l+pot;
	T[v][poz][0] = T[v][poz][1];
        
	while(v != 1){
		v /= 2;
		T[v][poz][0] = T[v*2][poz][0] + T[v*2+1][poz][0];
		if(T[v][poz][0] > dzielnik)
			T[v][poz][0] %= dzielnik;
	}
}
void poprawDol(int poz, rozmiar pot){
	rozmiar v = pot - 1;
	while(v >= 1){
		T[v][poz][1] = T[v*2][poz][1] + T[v*2+1][poz][1];
		if(T[v][poz][1] > dzielnik)
			T[v][poz][1] %= dzielnik;
		--v;
	}
}

int main(){
	rozmiar n;
	int k;
	std::cin >> n >> k;
	rozmiar pot = 1;
	while(pot < n)
		pot*=2;
	
	for (rozmiar i = 0; i < n; i++){
		std::cin >> TW[i];
		--TW[i];
	}
	inicjuj(n, pot);
        
	for(int i = 1 ; i < k ; ++i){
		popraw(TW[n-i],i-1,pot);
		for(rozmiar j = n - i - 1; j >= 0; j--){
			if(TW[j] != 0)
				T[pot+TW[j]][i][1] = wartosc(0,TW[j],i-1,pot);
			popraw(TW[j],i-1,pot);
		}
		poprawDol(i,pot);
	}
	std::cout << T[1][k-1][1] << std::endl;
        
	return 0;
}