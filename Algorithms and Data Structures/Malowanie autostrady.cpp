#include <iostream>

typedef long typ;

const typ maxN = 2*1024 * 1024;
typ n;
typ T[maxN * 2][2];
#define max(a, b) (a > b ? a : b)
#define min(a, b) (a < b ? a : b)

typ insert(typ p, typ k, typ ip, typ ik, typ i, typ w) {
	if (ip == ik) {
		T[i][0] = w;
		T[i][1] = 1;
		return T[i][0];
	}
	
	if ((p == ip) && (k == ik)) {
		if (w*(k - p + 1) == T[i][0])
			return T[i][0];
		T[i][0] = w*(k - p + 1);
		T[i][1] = 1;
		return T[i][0];
	}
	
	if (T[i][1] == 1) {
		T[i * 2][0] = T[i][0] / 2;
		T[i * 2 + 1][0] = T[i][0] / 2;
		T[i][1] = 0;
		T[i * 2][1] = 1;
		T[i * 2 + 1][1] = 1;
	}
	typ s = (ip + ik) / 2;
	if (p <= s)
		insert(p, min(k, s), ip, s, i * 2, w);
        
	if (k > s)
		insert(max(p, s + 1), k, s + 1, ik, i * 2 + 1, w);

	T[i][0] = T[i * 2][0] + T[i * 2 + 1][0];
	return 0;
}
typ max;
void insert(typ p, typ k, typ w) {
	insert(p, k, 1, max, 1, w);
}

int main() {
	typ k, p1, p2;

	char c;
	std::cin >> n >> k;
	max = 2;
	while (max < 2 * n)
		max *= 2;
	for (typ i = 0; i < k; i++) {
		std::cin >> p1 >> p2 >> c;
		insert(p1, p2, c == 'C' ? 0 : 1);
                
		std::cout << T[1][0] << std::endl;
	}
	return 0;
}