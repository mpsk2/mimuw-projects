#include <iostream>
#include <stdio.h>

int main(){

	char last, current;
	long long shortest, length, result, currentLenght;
	
	result = 0;
	
	current = last = getchar();
	length = 0;
	shortest = 1500000;
	currentLenght = 0;
	while (current == '*'){
		current = last = getchar();
		currentLenght++;
		
		length++;
	}
	while((current == '*') || isupper(current)){
		
		length++;
		
		if (current == '*'){
			currentLenght++;
		} else if (current == last) {
			currentLenght = 0;
		} else {
			if (currentLenght < shortest){
				shortest = currentLenght;
			}
			currentLenght = 1;
			
			last = current;
		}
		
		current = getchar();
	}
	
	if (shortest != 1500000)
		result = length - shortest > 0 ? length - shortest : 1;
	else
		result = 1;
	std::cout << result;
	
	return 0;
	
}