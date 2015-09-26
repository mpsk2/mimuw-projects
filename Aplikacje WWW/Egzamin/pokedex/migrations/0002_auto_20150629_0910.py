# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('pokedex', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Comment',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('author', models.CharField(max_length=64, verbose_name=b'Pseudonim autora')),
                ('content', models.CharField(max_length=512, verbose_name=b'Tre\xc5\x9b\xc4\x87')),
                ('date', models.DateTimeField(auto_now_add=True)),
            ],
            options={
                'ordering': ['-date'],
                'verbose_name': 'Komentarz',
                'verbose_name_plural': 'Komentarze',
            },
        ),
        migrations.AlterModelOptions(
            name='pokemon',
            options={'verbose_name': 'Pokemon', 'verbose_name_plural': 'Pokemony'},
        ),
        migrations.AlterModelOptions(
            name='skutecznosc',
            options={'verbose_name': 'Skuteczno\u015b\u0107', 'verbose_name_plural': 'Skuteczno\u015bci'},
        ),
        migrations.AddField(
            model_name='comment',
            name='pokemon',
            field=models.ForeignKey(verbose_name=b'Pokomen', to='pokedex.Pokemon'),
        ),
    ]
