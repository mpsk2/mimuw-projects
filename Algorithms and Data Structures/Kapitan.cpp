#include <iostream>
#include <queue>
#include <vector>
#include <algorithm>
#include <forward_list>
#include <functional>

int odl(std::pair<int, int> lewy, std::pair<int, int> prawy){
	return std::min(std::abs(lewy.first - prawy.first), std::abs(lewy.second - prawy.second));
}

typedef std::pair<std::pair<int, int>, std::pair<int, int> > czworka;

class{
public:
	bool operator() (czworka lewy, czworka prawy) {
		if (lewy.first.first != prawy.first.first)
			return lewy.first.first < prawy.first.first;
		else
			return lewy.first.second < prawy.first.second;
	}
} com1;

class{
public:
	bool operator() (czworka lewy, czworka prawy) {
		if (lewy.first.second != prawy.first.second)
			return lewy.first.second < prawy.first.second;
		else
			return lewy.first.first < prawy.first.first;
	}
} com2;

class{
public:
	bool operator() (czworka lewy, czworka prawy) { return lewy.second.first < prawy.second.first; }
} com3;


int main(){
	int n;

	std::cin >> n;

	std::vector<czworka> punkty(n); //x, y, numer, wyniki
	std::vector<std::forward_list<int> > listy_sasiedztwa(n);
	std::priority_queue<
            std::pair<int, int>, 
            std::vector<std::pair<int, int> >, 
            std::greater<std::pair<int, int> > > kolejka;

	for (int i = 0; i < n; i++){
		std::cin >> punkty[i].first.first >> punkty[i].first.second;
		punkty[i].second.first = i;
		punkty[i].second.second = 1 * 1000 * 1000 * 1000 + 6;
                
	}
	punkty[0].second.second = 0;
        

	std::sort(punkty.begin(), punkty.end(), com1);
        

	listy_sasiedztwa[punkty[0].second.first].push_front(punkty[1].second.first);
	listy_sasiedztwa[punkty[n-1].second.first].push_front(punkty[n-2].second.first);

	for (int i = 1; i < n - 1; i++){
		listy_sasiedztwa[punkty[i].second.first].push_front(punkty[i - 1].second.first);
		listy_sasiedztwa[punkty[i].second.first].push_front(punkty[i + 1].second.first);
	}
	

	std::sort(punkty.begin(), punkty.end(), com2);
        

	listy_sasiedztwa[punkty[0].second.first].push_front(punkty[1].second.first);
	listy_sasiedztwa[punkty[n - 1].second.first].push_front(punkty[n - 2].second.first);

	for (int i = 1; i < n - 1; i++){
		listy_sasiedztwa[punkty[i].second.first].push_front(punkty[i - 1].second.first);
		listy_sasiedztwa[punkty[i].second.first].push_front(punkty[i + 1].second.first);
	}
	

	std::sort(punkty.begin(), punkty.end(), com3);
        

	kolejka.push(std::make_pair(0, 0));

	while (!kolejka.empty()){
		auto w = kolejka.top();
		kolejka.pop();

		auto pom = punkty[w.second];
		for (auto const i : listy_sasiedztwa[pom.second.first]){
			int pom2 = pom.second.second + odl(punkty[i].first, punkty[w.second].first);
			if (pom2 < punkty[i].second.second){
				punkty[i].second.second = pom2;
				kolejka.push(std::make_pair(pom2, i));
			}
		}
	}

	std::cout << punkty[n - 1].second.second << std::endl;
	
	return 0;
}