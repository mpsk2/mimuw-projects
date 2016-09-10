# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Circuit',
            fields=[
                ('number', models.PositiveIntegerField(serialize=False, verbose_name='Numer', primary_key=True)),
                ('name', models.CharField(verbose_name='Nazwa', max_length=255)),
                ('kaczynski_votes', models.PositiveIntegerField(verbose_name='Głosy oddane na Kaczyńskiego')),
                ('komorowski_votes', models.PositiveIntegerField(verbose_name='Głosy oddane na Komorowskiego')),
                ('father', models.ForeignKey(to='circuits.Circuit', verbose_name='Nadobwód', null=True, blank=True)),
            ],
            options={
                'verbose_name': 'Obwód',
                'verbose_name_plural': 'Obwody',
            },
        ),
        migrations.CreateModel(
            name='Commission',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('number', models.PositiveIntegerField(verbose_name='Numer')),
                ('name', models.CharField(verbose_name='Nazwa', max_length=255)),
                ('kaczynski_votes', models.PositiveIntegerField(verbose_name='Głosy oddane na Kaczyńskiego')),
                ('komorowski_votes', models.PositiveIntegerField(verbose_name='Głosy oddane na Komorowskiego')),
                ('ballots_got', models.PositiveIntegerField(verbose_name='Otrzymane karty do głosowania', null=True, blank=True)),
                ('allowed_to_voted', models.PositiveIntegerField(verbose_name='Uprawnieni do głosowania', null=True, blank=True)),
                ('circuit', models.ForeignKey(to='circuits.Circuit', verbose_name='Obwód')),
            ],
            options={
                'verbose_name': 'Komisja wyborcza',
                'verbose_name_plural': 'Komisje wyborcze',
            },
        ),
        migrations.AlterIndexTogether(
            name='commission',
            index_together=set([('number', 'circuit'), ('circuit',)]),
        ),
        migrations.AlterIndexTogether(
            name='circuit',
            index_together=set([('number',), ('father',), ('name',)]),
        ),
    ]
