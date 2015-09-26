from django.db import models

# Create your models here.

class Circuit(models.Model):
    number = models.PositiveIntegerField(primary_key=True, verbose_name='Numer')
    name = models.CharField(max_length=255, verbose_name='Nazwa')
    father = models.ForeignKey("self", null=True, blank=True, verbose_name='Nadobwód')

    kaczynski_votes = models.PositiveIntegerField(verbose_name='Głosy oddane na Kaczyńskiego')
    komorowski_votes = models.PositiveIntegerField(verbose_name='Głosy oddane na Komorowskiego')

    def __str__(self):
        return self.name

    class Meta:
        index_together = [
            ['number'],
            ['name'],
            ['father']
        ]
        verbose_name = 'Obwód'
        verbose_name_plural = 'Obwody'


class Commission(models.Model):
    number = models.PositiveIntegerField(verbose_name='Numer')
    name = models.CharField(max_length=255, verbose_name='Nazwa')
    circuit = models.ForeignKey(Circuit, verbose_name='Obwód')

    kaczynski_votes = models.PositiveIntegerField(verbose_name='Głosy oddane na Kaczyńskiego')
    komorowski_votes = models.PositiveIntegerField(verbose_name='Głosy oddane na Komorowskiego')

    ballots_got = models.PositiveIntegerField(null=True, blank=True, verbose_name='Otrzymane karty do głosowania')
    allowed_to_voted = models.PositiveIntegerField(null=True, blank=True, verbose_name='Uprawnieni do głosowania')

    def __str__(self):
        return 'komisja: ' + self.name + ' w ' + str(self.circuit)

    class Meta:
        index_together = [
            ['number', 'circuit'],
            ['circuit'],
        ]
        verbose_name = 'Komisja wyborcza'
        verbose_name_plural = 'Komisje wyborcze'