#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.shortcuts import render
from models import *
from .forms import *
from django.http import HttpResponseRedirect
from django.http import HttpResponse

import json
from django.template.context_processors import csrf
from django.views.decorators.csrf import csrf_protect, ensure_csrf_cookie

def showList(request):
    pokemony = Pokemon.objects.all()
    return render(request, 'list.html', locals())


@ensure_csrf_cookie
def details(request, pokemon_id):
    context = {}
    try:
        pokemon = Pokemon.objects.get(numer=pokemon_id)
        context['pokemon'] = pokemon

        usage = Skutecznosc.objects.values('atak').distinct()
        context['usage'] = usage

        if request.method == 'POST':
            comment = Comment(pokemon = pokemon)
            form = CommentForm(request.POST, instance=comment)
            context['form'] = form
            if form.is_valid():
                try:
                    form.save()
                    return HttpResponseRedirect("/details/%s" % pokemon_id)
                except Exception as ex:
                    print(str(ex))
            else:
                print('Is not valid')
        else:
            comment = Comment(pokemon = pokemon)
            context['form'] = CommentForm(instance=pokemon)
    except Exception as ex:
        print(str(ex))

    return render(request, 'details.html', context)


def ajax_power_count(request, pokemon_id, attack_id):
    response_data = { }
    try:
        wyn = 1
        pokemon = Pokemon.objects.get(numer=pokemon_id)
        try:
            skt1 = Skutecznosc.objects.get(atak=attack_id, obrona=pokemon.rodzaj1)
            wyn = wyn * int(skt1.mnoznik)
        except Exception as ex:
            print(str(ex))
        try:
            if pokemon.rodzaj2:
                skt2 = Skutecznosc.objects.get(atak=attack_id, obrona=pokemon.rodzaj2)
                wyn = wyn * int(skt2.mnoznik)
        except Exception as ex:
            print(str(ex))
        response_data['power'] = wyn
    except Exception as ex:
        response_data['power'] = "Nie ma takiej relacji"
        print(str(ex))

    return HttpResponse(
        json.dumps(response_data),
        content_type="application/json"
    )
