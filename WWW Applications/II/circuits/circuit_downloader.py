# first we need to import libraries

# to get page
try:
    # For Python 3.0 and later
    from urllib.request import urlopen
except ImportError:
    # Fall back to Python 2's urllib2
    from urllib2 import urlopen

# to parse page
from bs4 import BeautifulSoup

# class that will contain election circuit
class ElectionCircuit:
	ELECTION_LINK = "http://prezydent2010.pkw.gov.pl/PZT/PL/WYN/W/"
	
	# contructor takes needed elements
	def __init__(
		self,
		details_link,
		number = 0,
		name = "",
		kaczynski_votes = 0,
		komorowski_votes = 0,
		father = None,
		children = None):
		""" constructor of ElectionCircuit
		
		Keyword arguments:
		details_link -- link of page to go deeper to get details
		number -- circuit number (default 0)
		name -- circuit name (default "")
		kaczynski_votes -- votes Kaczynski got (default 0)
		komorowski_votes -- votes komorowski got (default 0)
		father -- circuit that contains that circuit (default None)
		children -- list of circuit that are contained in that circuit (None)
		"""
		
		self.details_link = details_link
		self.number = number
		self.name = name
		self.kaczynski_votes = kaczynski_votes
		self.komorowski_votes = komorowski_votes
		self.father = father
		if children is None:
			self.children = list()
		else:
			self.children = children
		
	def add_child(self, child):
		"""add child to circuit
		
		Keyword arguments:
		child -- child to be add
		"""
		
		self.children.append(child)
		pass
	
	def is_link_correct(self):
		"""tells you if details_link allow to go deeper"""
		return not "../" in self.details_link
	
	def get_full_link(self):
		"""return link to go deeper, does not check if correct """
		return str(ElectionCircuit.ELECTION_LINK) + str(self.details_link)

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
	"""get page from given url 
	
	Keyword arguments:
	url -- url to get page, does no check if correct
	"""
	return urlopen(url)

def get_rows(page):
	"""get rows containing informations we want
	
	Keyword arguments:
	page -- page to get rows, does not check if correct
	"""
	return BeautifulSoup(page).find(id='s0').tbody.findAll('tr')

def parse_row_to_circuit(row, father = None):
	"""get row and parse it into ElectionCircuit object
	1st column of row contains number
	2nd column of row contains name and link
	4th column of row contains kaczynski_votes
	8th column of row contains komorowski_votes
	
	Keyword arguments:
	row -- row to parse
	"""
	cells = row.findAll("td")
	return ElectionCircuit(
		str(cells[1].contents[0]['href']),
		int(cells[0].contents[0]),
		cells[1].contents[0].string.encode("utf-8"),
		int(cells[3].contents[0]),
		int(cells[7].contents[0]),
		father)

def for_father_get_all_childer(father):
	"""for father gets his details_link and than get his all children
	
	Keyword arguments:
	father --
	"""
	page = get_page(father.get_full_link())
	rows = get_rows(page)
	
	for row in rows:
		try:
			child = parse_row_to_circuit(row)

			if child.number > 100:
			    print("po " + str(child.number))
			
			if child.is_link_correct():
				child = for_father_get_all_childer(child)
			father.add_child(child)
		except TypeError as er:
			# this will go when first column is " " for table Zagranica, but we will get_full_link
			# circuits
			pass

	return father