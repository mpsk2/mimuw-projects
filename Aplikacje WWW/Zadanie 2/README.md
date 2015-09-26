Zadanie na aplikacje www
nale�y miec zainstalowanego pythona3, vitualenv na nim, django, BeautifulSoup4, gunicorn psycopg2
```bash
yum -y install python python3 python-setuptools
pip install virtualenv
virtualenv -p python3 ~/.virtualenvs/pyworks3
source ~/.virtualenvs/pyworks3/bin/activate
```
Pobrac projekt, przejsc do projektu i pobrac zaleznosci
```bash
pip install -r requirements.txt
```
A nastepnie pobrac projekt i wykonac migracje
```bash
./manage.py makemigrations circuits
./manage.py sqlmigrate circuits 0001
./manage.py migrate
```

1. Zmiany
1 - Dodanie przycisku powrotu
2 - Błąd na tej samej stonie