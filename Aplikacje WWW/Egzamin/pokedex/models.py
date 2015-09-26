#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.db import models

class Pokemon(models.Model):
    numer = models.PositiveIntegerField(primary_key=True)
    nazwa = models.CharField(max_length=32, unique=True)
    #ewolucja = models.CharField(max_length=32, blank=True)
    ewolucja = models.ForeignKey('self', blank=True)
    rodzaj1 = models.CharField(max_length=32)
    rodzaj2 = models.CharField(max_length=32, blank=True)

    def __unicode__(self):
        return u'#{0} {1}'.format(self.numer, self.nazwa)

    class Meta:
        verbose_name = 'Pokemon'
        verbose_name_plural = 'Pokemony'

class Skutecznosc(models.Model):
    atak = models.CharField(max_length=32)
    obrona = models.CharField(max_length=32)
    mnoznik = models.FloatField()

    def __unicode__(self):
        return u'{0} x {1}'.format(self.atak, self.obrona)

    class Meta:
        unique_together = ('atak', 'obrona')
        verbose_name = 'Skuteczność'
        verbose_name_plural = 'Skuteczności'


class Comment(models.Model):
    author = models.CharField(max_length=64, verbose_name='Pseudonim autora')
    content = models.CharField(max_length=512, verbose_name='Treść')
    pokemon = models.ForeignKey(Pokemon, verbose_name='Pokomen')
    date = models.DateTimeField(auto_now_add=True, blank=True)

    class Meta:
        verbose_name = 'Komentarz'
        verbose_name_plural = 'Komentarze'
        ordering = ['-date']