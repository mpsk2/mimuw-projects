#include <iostream>
#include <ctype.h>

const int max = 1001;
#define MILIARD (1000 * 1000 * 1000)

int main(){

	int tablica[max][max][2];
	int ciag[max];
	int n;

	for (int i = 0; i < max; i++){
		for (int j = 0; j < max; j++){
			if (i != j){
				tablica[i][j][0] = 0;
				tablica[i][j][1] = 0;
			}
			else {
				tablica[i][j][0] = 1;
				tablica[i][j][1] = 0;
			}
		}
	}

	std::cin >> n;
	for (int i = 0; i < n; i++)
		std::cin >> ciag[i];

	for (int i = n - 1; i >= 0; i--){
		for (int j = i + 1; j < n; j++){
			tablica[i][j][0] = (
                            (ciag[i] < ciag[i + 1] ? tablica[i + 1][j][0] : 0) + 
                            (ciag[i] < ciag[j] ? tablica[i + 1][j][1] : 0)) % MILIARD;
			tablica[i][j][1] = (
                            (ciag[j] > ciag[i] ? tablica[i][j - 1][0] : 0) + 
                            (ciag[j] > ciag[j - 1] ? tablica[i][j - 1][1] : 0)) % MILIARD;
		}
	}

	std::cout << ((tablica[0][n - 1][0] + tablica[0][n - 1][1])) % MILIARD << std::endl;

	return 0;
}
