# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations

def change(apps, schema_editor):
    Pokemon = apps.get_model('pokedex', 'Pokemon')
    for p in Pokemon.objects.all():
        if p.ewolucja:
            try:
                p.next_one = Pokemon.objects.get(nazwa=p.ewolucja)
                p.save()
            except Exception as ex:
                print(str(ex))


class Migration(migrations.Migration):

    dependencies = [
        ('pokedex', '0002_auto_20150629_0910'),
    ]

    operations = [
        migrations.AddField(
            model_name='pokemon',
            name='next_one',
            field=models.ForeignKey(to='pokedex.Pokemon', blank=True, null=True),
        ),
        migrations.RunPython(change),
        migrations.RemoveField(
            model_name='pokemon',
            name='ewolucja'
        ),
        migrations.RenameField(
            model_name='pokemon',
            old_name='next_one',
            new_name='ewolucja'
        ),
    ]
