from django.db import models

# Create your models here.


class ElectoralCircuit(models.Model):
    number = models.PositiveIntegerField(primary_key = True)
    name = models.CharField(max_length = 255)
    super_circuit = models.ForeignKey("self", null = True, blank = True)

    kaczynski_votes = models.PositiveIntegerField()
    komorowski_votes = models.PositiveIntegerField()

    def __str__(self):
        return self.name


class ElectoralCommission(models.Model):
    number = models.PositiveIntegerField()
    name = models.CharField(max_length = 255)
    circuit = models.ForeignKey(ElectoralCircuit, null = True, blank = True)

    kaczynski_votes = models.PositiveIntegerField()
    komorowski_votes = models.PositiveIntegerField()

    ballots_got = models.PositiveIntegerField(null = True, blank = True)
    allowed_to_vote = models.PositiveIntegerField(null = True, blank = True)

    def __str__(self):
        return self.name