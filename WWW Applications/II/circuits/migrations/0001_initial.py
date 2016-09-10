# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='ElectoralCircuit',
            fields=[
                ('number', models.PositiveIntegerField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255)),
                ('kaczynski_votes', models.PositiveIntegerField()),
                ('komorowski_votes', models.PositiveIntegerField()),
                ('super_circuit', models.ForeignKey(to='circuits.ElectoralCircuit', blank=True, null=True)),
            ],
        ),
        migrations.CreateModel(
            name='ElectoralCommission',
            fields=[
                ('id', models.AutoField(verbose_name='ID', primary_key=True, auto_created=True, serialize=False)),
                ('number', models.PositiveIntegerField()),
                ('name', models.CharField(max_length=255)),
                ('kaczynski_votes', models.PositiveIntegerField()),
                ('komorowski_votes', models.PositiveIntegerField()),
                ('ballots_got', models.PositiveIntegerField(blank=True, null=True)),
                ('allowed_to_vote', models.PositiveIntegerField(blank=True, null=True)),
                ('circuit', models.ForeignKey(to='circuits.ElectoralCircuit', blank=True, null=True)),
            ],
        ),
    ]
