from django.shortcuts import render

from django.db import transaction

from circuits.models import *

import json
from django.http import HttpResponse
from django.template.context_processors import csrf
from django.views.decorators.csrf import csrf_protect, ensure_csrf_cookie


# Create your views here.
def index(request):
    children = Circuit.objects.filter(father__isnull=True)

    context = { }
    context['circuit'] = None
    context['circuits'] = children
    context['next_circuit'] = True

    return render(request, 'circuits/circuits.html', context)


@ensure_csrf_cookie
def circuits(request, circuit_id):
    master = Circuit.objects.get(number=circuit_id)
    children = Circuit.objects.filter(father=circuit_id)
    next_circuit = True

    if not children:
        children = Commission.objects.filter(circuit=circuit_id)
        next_circuit = False

    context = { }
    context.update(csrf(request))
    context['circuit'] = master
    context['circuits'] = children
    context['next_circuit'] = next_circuit

    return render(request, 'circuits/circuits.html', context)


def ajax_get_commission(request, commission_id):
    response_data = { }
    response_data['number'] = commission_id

    c = Commission.objects.get(id=commission_id)

    response_data['ballots_got'] = c.ballots_got
    response_data['allowed_to_voted'] = c.allowed_to_voted

    return HttpResponse(
        json.dumps(response_data),
        content_type="application/json"
    )


@csrf_protect
def ajax_post_commission(request, commission_id):
    if request.method == 'POST':
        ballots_got = 0
        allowed_to_voted = 0
        old_ballots_got = 0
        old_allowed_to_voted = 0
        try:
            try:
                ballots_got = int(request.POST.get('ballots_got'))
                if ballots_got == "":
                    ballots_got = 0
            except Exception:
                ballots_got = 0

            try:
                allowed_to_voted = int(request.POST.get('allowed_to_voted'))
                if allowed_to_voted == "":
                    allowed_to_voted = 0
            except Exception:
                allowed_to_voted = 0

            try:
                old_ballots_got = int(request.POST.get('old_ballots_got'))
                if old_ballots_got == "":
                    old_ballots_got = 0
            except Exception:
                old_ballots_got = 0

            try:
                old_allowed_to_voted = int(request.POST.get('old_allowed_to_voted'))
                if old_allowed_to_voted == "":
                    old_allowed_to_voted = 0
            except Exception:
                old_allowed_to_voted = 0

            response_data = { }
            response_data['number'] = commission_id

            if allowed_to_voted < 0 or ballots_got < 0:
                raise Exception("Dałeś ujemne")

            with transaction.atomic():
                c = Commission.objects.select_for_update().get(pk=commission_id)
                if c.allowed_to_voted is not None and c.allowed_to_voted != old_allowed_to_voted:
                    raise Exception("Ktoś cię wyprzedził")
                if c.allowed_to_voted is not None and c.ballots_got != old_ballots_got:
                    raise Exception("Ktoś cię wyprzedził")
                c.ballots_got = ballots_got
                c.allowed_to_voted = allowed_to_voted
                c.save()

            response_data['ballots_got'] = c.ballots_got
            response_data['allowed_to_voted'] = c.allowed_to_voted
            response_data['error_message'] = 'Brak błędu'

            return HttpResponse(
                json.dumps(response_data),
                content_type="application/json"
            )
        except Exception as ex:
            c = Commission.objects.get(pk=commission_id)
            ballots_got = c.ballots_got
            allowed_to_voted = c.allowed_to_voted
            old_ballots_got = c.ballots_got
            old_allowed_to_voted = c.allowed_to_voted
            response_data = {
                'error_message' : str(ex),
                'ballots_got' : ballots_got,
                'allowed_to_voted' : allowed_to_voted,
                'old_ballots_got' : old_ballots_got,
                'old_allowed_to_voted' : old_allowed_to_voted,
            }
            return HttpResponse(
                json.dumps(response_data),
                content_type="application/json"
            )
    else:
        return None