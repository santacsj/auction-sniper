Vagrant.configure(2) do |config|
  
  config.vm.box = "ubuntu/trusty64"
  
  config.vm.define "xmpp-server" do |xmpp|
  	xmpp.vm.network :forwarded_port, guest: 5222, host: 5222
  end 
  
  config.vm.provision :ansible do |ansible|
  	ansible.playbook = "environment/main.yml"
  end
  
end
