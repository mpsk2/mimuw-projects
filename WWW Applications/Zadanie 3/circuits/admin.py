from django.contrib import admin

# Register your models here.
from .models import *

class CircuitsAdmin(admin.ModelAdmin):
    list_display = ('number', 'name', 'kaczynski_votes', 'komorowski_votes')
    search_fields = ('number', 'name', 'father')


class CommissionsAdmin(admin.ModelAdmin):
    list_display = ('number', 'name', 'kaczynski_votes', 'komorowski_votes', 'ballots_got', 'allowed_to_voted')
    search_fields = ('number', 'name')


admin.site.register(Circuit, CircuitsAdmin)
admin.site.register(Commission, CommissionsAdmin)