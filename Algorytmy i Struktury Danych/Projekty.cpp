#include <queue>
#include <vector>
#include <forward_list>
#include <iostream>
#include <functional>

int main(){
	int n, m, k;
	std::cin >> n >> m >> k;
	std::vector<int> potrzebni_programisci(n);
	std::vector<std::forward_list<int> > zaleznosci(n);
	std::vector<int> liczba_wejsc(n);
        
	std::priority_queue<
            std::pair<int, int>, 
            std::vector<std::pair<int, int> >, 
            std::greater<std::pair<int, int> > > kolejka;
            
	std::vector<int> najlepszy(n);

	for (int i = 0; i < n; i++){
		std::cin >> potrzebni_programisci[i];
	}

	for (int i = 0; i < m; i++){
		int a, b;
		std::cin >> a >> b;
		liczba_wejsc[a - 1]++;
		zaleznosci[b - 1].push_front(a - 1);
	}

	for (int i = 0; i < n; i++){
		if (liczba_wejsc[i] == 0){
			kolejka.push(std::make_pair(potrzebni_programisci[i], i));
			najlepszy[i] = potrzebni_programisci[i];
		}
		else
			najlepszy[i] = 0;
	}

	for (int i = 0; i < k-1; i++){
		const auto pom = kolejka.top();
		kolejka.pop();
		for (const auto s : zaleznosci[pom.second]){
			liczba_wejsc[s]--;
			najlepszy[s] = std::max(
                            najlepszy[s], 
                           std::max(najlepszy[pom.second], potrzebni_programisci[s]));
			if (liczba_wejsc[s] == 0){
				kolejka.push(std::make_pair(najlepszy[s], s));
			}
		}
	}
	std::cout << kolejka.top().first << std::endl;

	return 0;
}