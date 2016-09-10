__author__ = 'mstankiewicz'

from django.conf.urls import patterns, url

from circuits import views

urlpatterns = patterns(
    '',
    url(r'^$', views.index, name='circuit_index'),
    url(r'^circuit/(?P<circuit_id>\d+)/$', views.circuits, name='circuit'),
    url(r'^commission/(?P<commission_id>\d+)/$', views.commission, name='commission'),
    url(r'^edit_commission/(?P<commission_id>\d+)/$', views.edit_commission, name='edit_commission'),
    )
