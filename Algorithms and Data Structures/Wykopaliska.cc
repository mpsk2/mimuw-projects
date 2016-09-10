#include <iostream>
#include <climits>

typedef long long typ;

typ MAX2(typ A, typ B) { return A > B ? A : B; }
typ MAX3(typ A, typ B, typ C) { return MAX2(MAX2(A, B), C); }
typ MAX4(typ A, typ B, typ C, typ D) { return MAX2(MAX2(A, B), MAX2(C, D)); }

const typ dol = LLONG_MIN;

const typ maxN = 50 * 100;
const typ maxK = 50 * 100;

typ W[maxN][3];
typ T[2][maxK+2][3]; //T[i][1][j] odpowiada 0 wybranym, T[i][k+1][j] - wzięcie k
int n, k;

void clearT();

typ przerob(typ);

void pobierzW();
void przesunT();

int main(){
	std::cin >> n >> k;
	pobierzW();
        
	typ w1, w2, w3;
        
	w1 = przerob(0);
	w2 = przerob(1);
	w3 = przerob(2);
        
	typ w = MAX2(0, w1);
        
	w = MAX2(w, w2);
	w = MAX2(w, w3);
        
	std::cout << w << std::endl;
	return 0;
}

void clearT() {
	for(int i = 0; i < 2; i++){
		for(int j = 0; j < k+2; j++){
			for(int l = 0; l < 3; l++)
				T[i][j][l] = dol;
		}
	}
}

typ przerob(typ p) {
    
	clearT();
        
	switch(p){
		case 0:// lewy wyjazd
			T[1][2][0] = MAX2(W[0][1], W[0][2]);
			T[1][3][0] = W[0][1] + W[0][2];
			T[1][2][1] = W[0][2];
			T[1][1][0] = 0;
			T[1][1][1] = 0;
			T[1][1][2] = 0;
			break;
		case 1:
			T[1][2][0] = W[0][2];
			T[1][2][1] = MAX2(W[0][0], W[0][2]);
			T[1][3][1] = W[0][0] + W[0][2];
			T[1][2][2] = W[0][0];
			T[1][1][0] = 0;
			T[1][1][1] = 0;
			T[1][1][2] = 0;
			break;
		case 2: //prawy wyjazd
			T[1][2][1] = W[0][0];
			T[1][2][2] = MAX2(W[0][0], W[0][1]);
			T[1][3][2] = W[0][0] + W[0][1];
			T[1][1][0] = 0;
			T[1][1][1] = 0;
			T[1][1][2] = 0;
			break;
		default:
			return 0;
	}
	
	przesunT();
        
	
	for(int i = 1; i < n; i++){
		for(int j = 2; j < k+2; j++){
			T[1][j][0] = MAX3(
				MAX3(T[0][j][0], T[0][j][1], T[0][j][2]),
				MAX2(W[i][1] + T[0][j-1][0], W[i][2] + MAX2(T[0][j-1][0], T[0][j-1][1])),
				W[i][1] + W[i][2] + T[0][j-2][0]);
			T[1][j][1] = MAX3(
				MAX3(T[0][j][0], T[0][j][1], T[0][j][2]),
				MAX2(W[i][0] + MAX2(T[0][j-1][1], T[0][j-1][2]), W[i][2] + MAX2(T[0][j-1][0], T[0][j-1][1])),
				W[i][0] + W[i][2] + T[0][j-2][1]);
			T[1][j][2] = MAX3(
				MAX3(T[0][j][0], T[0][j][1], T[0][j][2]),
				MAX2(W[i][1] + T[0][j-1][2], W[i][0] + MAX2(T[0][j-1][2], T[0][j-1][1])),
				W[i][0] + W[i][1] + T[0][j-2][2]);
		}
		przesunT();
                
	}
	
	typ wyn = 0;
	for(int i = 0; i < k+2; i++)
		wyn = MAX2(wyn, T[0][i][p]);
        
	return wyn;
}

void przesunT() {
		for(int j = 0; j < k+2; j++){
			T[0][j][0] = T[1][j][0];
			T[1][j][0] = dol;
			T[0][j][1] = T[1][j][1];
			T[1][j][1] = dol;
			T[0][j][2] = T[1][j][2];
			T[1][j][2] = dol;
		}
}

void pobierzW() {
	for(int i = 0; i < n; i++)
		std::cin >> W[i][0] >> W[i][1] >> W[i][2];
}