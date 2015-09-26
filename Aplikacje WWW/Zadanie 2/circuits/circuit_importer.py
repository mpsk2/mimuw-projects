from circuits.circuit_downloader import *
from circuits.models import *

def regist_commissions(master, master_db):
	for child in master.children:
		c = ElectoralCommission(
			number = child.number,
			name = child.name, 
			circuit = master_db,
			kaczynski_votes = child.kaczynski_votes,
			komorowski_votes = child.komorowski_votes)
		c.save()


def register_circuits(master, master_db = None):
	for child in master.children:
		c = ElectoralCircuit(
            number = child.number,
            name = child.name,
            super_circuit = master_db,
			kaczynski_votes = child.kaczynski_votes,
			komorowski_votes = child.komorowski_votes)
		c.save()
		if not child.children[0].children:
			regist_commissions(child, c)
		else:
			register_circuits(child, c)


def import_all():
	master = for_father_get_all_childer(ElectionCircuit("index.htm"))
	register_circuits(master)
	pass