Vagrant.configure(2) do |config|
  
  config.vm.box = "ubuntu/trusty64"
  
  config.vm.define "openfire-server" do |openfire|
  	openfire.vm.network :forwarded_port, guest: 9090, host: 9090	# Admin console
  end 
  
  config.vm.provision :ansible do |ansible|
  	ansible.playbook = "environment/main.yml"
  end
  
end
