#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.contrib import admin

# Register your models here.
from .models import *

class PokemonsAdmin(admin.ModelAdmin):
    list_display = ('numer', 'nazwa', 'ewolucja', 'rodzaj1', 'rodzaj2')


class SkutecznoscsAdmin(admin.ModelAdmin):
    list_display = ('atak', 'obrona', 'mnoznik')


class CommentssAdmin(admin.ModelAdmin):
    list_display = ('author', 'content', 'pokemon', 'date')


admin.site.register(Pokemon, PokemonsAdmin)
admin.site.register(Skutecznosc, SkutecznoscsAdmin)
admin.site.register(Comment, CommentssAdmin)