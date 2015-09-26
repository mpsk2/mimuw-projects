__author__ = 'mstankiewicz'

from urllib.request import urlopen

from bs4 import BeautifulSoup

import threading

from .models import *
from django.db import transaction

class ElectoralItem:
    ELECTION_LINK = "http://prezydent2010.pkw.gov.pl/PZT/PL/WYN/W/"

    def __init__(
            self,
            details_link,
            number=0,
            name='Cała polska',
            kaczynski_votes=0,
            komorowski_votes=0,
            father=None,
            children=None):

        self.details_link = details_link
        self.number = number
        self.name = name
        self.kaczynski_votes = kaczynski_votes
        self.komorowski_votes = komorowski_votes
        self.father = father
        if not children:
            self.children = list()
        else:
            self.children = children

    def add_child(self, child):
        self.children.append(child)

    def is_link_ok(self):
        return not "../" in self.details_link

    def get_full_link(self):
        return str(ElectoralItem.ELECTION_LINK) + str(self.details_link)

    def __str__(self):
        result = "Okreg: \n"
        result = result + "numer              : " + str(self.number) + "\n"
        result = result + "nazwa              : " + str(self.name) + "\n"
        result = result + "link do szczegolow : " + str(self.details_link) + "\n"
        result = result + "wynik kaczynskiego : " + str(self.kaczynski_votes) + "\n"
        result = result + "wynik komorowskiego: " + str(self.komorowski_votes) + "\n"
        result = result + "liczba podokregow  : " + str(len(self.children)) + "\n\n"
        return result


def get_page(url):
    return urlopen(url)


def get_rows(page):
    return BeautifulSoup(page).find(id='s0').tbody.findAll('tr')


def parse_row_for_item(row, father=None):
    cells = row.findAll('td')
    return ElectoralItem(
        str(cells[1].contents[0]['href']),
        int(cells[0].contents[0]),
        cells[1].contents[0].string.encode("utf-8"),
        int(cells[3].contents[0]),
        int(cells[7].contents[0]),
        father)


def register_commissions(master, master_db):
    c = Commission(
        number=master.number,
        name=master.name,
        circuit=master_db,
        kaczynski_votes=master.kaczynski_votes,
        komorowski_votes=master.komorowski_votes)
    c.save()


def register_first_circuit(master):
    c = Circuit(
        number=master.number,
        name=master.name,
        kaczynski_votes=master.kaczynski_votes,
        komorowski_votes=master.komorowski_votes,
        father=None
        )
    c.save()

    for child in master.children:
        t = threading.Thread(target=register_circuits, args=(child, c))
        t.daemon = False
        t.start()
    print('before save')


def register_circuits(master, master_db=None):
    for child in master.children:
        c = Circuit(
            number=master.number,
            name=master.name,
            father=master_db,
            kaczynski_votes=master.kaczynski_votes,
            komorowski_votes=master.komorowski_votes,
        )

        c.save()

        if not child.children:
            register_commissions(child, c)
        else:
            register_circuits(child, c)



def for_father_get_all_children(father):
    page = get_page(father.get_full_link())
    rows = get_rows(page)


    for row in rows:
        try:
            child = parse_row_for_item(row, father)

            if child.number > 300 and child.number % 250 == 0:
                print(child.number)

            if child.is_link_ok():
                child = for_father_get_all_children(child)

            if child.number % 10000 == 0:
                t = threading.Thread(target=register_first_circuit, args=(child,))
                t.daemon = False
                t.start()

            father.add_child(child)

        except TypeError as ex:
            pass

    return father


def import_all():
    return for_father_get_all_children(ElectoralItem('index.htm'))


import_all()