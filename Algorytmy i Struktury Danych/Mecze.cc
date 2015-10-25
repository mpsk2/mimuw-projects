#include <iostream>

const unsigned long maxN = 40000;
const unsigned long maxK = 50;

int main(){
	int n, k;
        
	unsigned long long lb[maxN];
	int pom;	
	std::cin>>n>>k;
	unsigned long long s = 1;
	for(int i = 0 ; i < k ; i++){
		for(int j = 0 ; j < n/2 ; j++){
			std::cin>>pom;
                        
		}
		for(int j = n/2 ; j < n ; j++){
			std::cin>>pom;
                        
			lb[pom-1] +=s;
		}
		s*=2;
	}
	//bool war;
	
	for(int i = 0 ; i < n; i++){
		for(int j = n-1 ; j > i; j--){
			if(lb[i] == lb[j]){
				std::cout << "NIE" << std::endl;
				return 0;
			}
		}
	}

	std::cout << "TAK" << std::endl;
	
	return 0;
}
