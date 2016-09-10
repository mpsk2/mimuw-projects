from django.conf.urls import patterns, include, url
from django.contrib import admin

from django.views.generic.base import TemplateView

urlpatterns = patterns('',
    url(r'^circuits/', include('circuits.urls')),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^about$', TemplateView.as_view(template_name='about.html'), name='about'),
    url(r'^$', TemplateView.as_view(template_name='index.html'), name='home'),
    )
